package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.SubmitFormUseCase;
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import com.civica.newhires.forms.domain.ports.output.UserIdentityPort;
import com.civica.newhires.forms.domain.ports.output.FileStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmitFormService implements SubmitFormUseCase {

    private final FormRepository formRepository;
    private final UserIdentityPort userIdentityPort;
    private final FileNameGenerator fileNameGenerator;
    private final FormValidator formValidator;
    private final FileStoragePort fileStoragePort;

    @Override
    @Transactional
    public void execute(String token, List<FieldResponseDTO> textResponses, Map<UUID, MultipartFile> files) {
        
        // 1. Validar token y obtener identidad del empleado
        // Asegúrate de que userIdentityPort devuelva un Optional<UUID>
        UUID employeeId = userIdentityPort.findEmployeeIdByToken(token)
                .orElseThrow(() -> new RuntimeException("Acceso no autorizado o token expirado"));

        List<FieldDefinition> definitions = formRepository.findAllFieldDefinitions();
        List<FieldValue> valuesToSave = new ArrayList<>();

        // 2. Procesar respuestas de texto (Campos normales)
        if (textResponses != null) {
            textResponses.forEach(dto -> {
                // Si fieldDefinitionId en el DTO es UUID, esto funcionará perfecto
                FieldDefinition def = findDefinition(definitions, dto.fieldDefinitionId());
                formValidator.validate(def, dto);
                valuesToSave.add(new FieldValue(def.getId(), employeeId, dto.value()));
            });
        }

        // 3. Procesar archivos físicos (PDFs, fotos...)
        if (files != null) {
            files.forEach((fieldId, file) -> {
                FieldDefinition def = findDefinition(definitions, fieldId);
                
                try {
                    // Generar nombre según requisitos
                    String newName = fileNameGenerator.generate(
                        def.getLabel(), "SOLICITANTE", "NUEVO", file.getOriginalFilename()
                    );

                    // Guardar físicamente
                    String savedPath = fileStoragePort.save(file, newName);
                    
                    // Guardar la referencia en la lista
                    valuesToSave.add(new FieldValue(def.getId(), employeeId, savedPath));
                    
                } catch (IOException e) {
                    throw new RuntimeException("Error crítico al guardar el documento: " + def.getLabel(), e);
                }
            });
        }

        // 4. Persistencia final de los valores
        formRepository.saveValues(valuesToSave);
        
        // 5. Registrar hito de entrega para RRHH
        // Verificamos que el modelo Submission acepte UUID en el constructor
        Submission submission = new Submission(employeeId);
        formRepository.saveSubmission(submission);
    }

    private FieldDefinition findDefinition(List<FieldDefinition> definitions, UUID id) {
        return definitions.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El campo con ID " + id + " no existe."));
    }
}