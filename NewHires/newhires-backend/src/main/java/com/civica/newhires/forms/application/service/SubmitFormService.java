package com.civica.newhires.forms.application.service;

import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.application.dto.FileInput;
import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.SubmitFormUseCase;
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import com.civica.newhires.forms.domain.ports.output.NotificationPort;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository; 
import com.civica.newhires.forms.domain.ports.output.FileStoragePort;
import com.civica.newhires.forms.domain.service.FormDomainService; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmitFormService implements SubmitFormUseCase {

    private final FormRepository formRepository; 
    private final SubmissionRepository submissionRepository; 
    private final UserIdentityPort userIdentityPort;
    private final FileStoragePort fileStoragePort;
    private final FormDomainService formDomainService; 
    private final NotificationPort notificationPort;

    @Override
    @Transactional
    public void execute(String token, List<FieldResponseDTO> textResponses, Map<UUID, FileInput> files) {
        
        // 1. Identificación del usuario con datos reales
        UUID employeeId = userIdentityPort.findEmployeeIdByToken(token)
                .orElseThrow(() -> new RuntimeException("Acceso no autorizado: Token inválido"));

        String employeeEmail = userIdentityPort.findEmailByToken(token)
                .orElseThrow(() -> new RuntimeException("No se encontró email para el token suministrado"));

        // Intentamos obtener el nombre real; si no existe, usamos el email como identificador
        String employeeName = userIdentityPort.findNameByToken(token)
                .orElse(employeeEmail.split("@")[0]); 

        List<FieldDefinition> definitions = formRepository.findAllFieldDefinitions();
        List<FieldValue> valuesToSave = new ArrayList<>();

        // 2. Procesar respuestas de texto
        if (textResponses != null) {
            textResponses.forEach(dto -> {
                UUID fieldId = parseUuid(dto.fieldDefinitionId());
                FieldDefinition def = findDefinition(definitions, fieldId);
                
                formDomainService.validateField(def, dto.value());
                valuesToSave.add(new FieldValue(def.getId(), employeeId, dto.value()));
            });
        }

        // 3. Procesar archivos
        if (files != null) {
            files.forEach((fieldUuid, fileInput) -> {
                FieldDefinition def = findDefinition(definitions, fieldUuid);
                
                try {
                    String newName = formDomainService.generateFileName(def.getLabel(), fileInput.fileName());
                    String savedPath = fileStoragePort.save(fileInput.content(), newName);
                    valuesToSave.add(new FieldValue(def.getId(), employeeId, savedPath));
                } catch (IOException e) {
                    throw new RuntimeException("Error al procesar el archivo: " + def.getLabel(), e);
                }
            });
        }

        // 4. Persistencia y Notificaciones
        formRepository.saveValues(valuesToSave);
        
        // Registro oficial de la entrega con todos los datos reales
        Submission submission = new Submission(employeeId, employeeName, employeeEmail, token);
        submissionRepository.save(submission);

        // Notificaciones automáticas
        notificationPort.sendSubmissionConfirmation(employeeEmail, employeeName);
        notificationPort.sendAdminNotification("rrhh@civica.com", employeeName);
    }

    private UUID parseUuid(Object id) {
        if (id instanceof UUID) return (UUID) id;
        return UUID.fromString(id.toString());
    }

    private FieldDefinition findDefinition(List<FieldDefinition> definitions, UUID id) {
        return definitions.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Definición de campo no encontrada: " + id));
    }
}