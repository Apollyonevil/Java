package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldValueRepository;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminSubmissionDetailController {

    private final SubmissionRepository submissionRepository;
    private final JpaFieldValueRepository fieldValueRepository;
    private final JpaFieldDefinitionRepository fieldDefinitionRepository;

    @GetMapping("/submissions/{id}/detail")
    public ResponseEntity<SubmissionDetailResponse> getDetail(@PathVariable UUID id) {
        // 1. Buscamos la submission. Al recuperarla, el adaptador ya nos da 
        // los datos del candidato (nombre, email) integrados.
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        // 2. Buscamos los valores del formulario usando el employeeId
        List<FieldValueEntity> values = fieldValueRepository.findByEmployeeId(submission.getEmployeeId());

        // 3. Mapeamos a DTO
        List<FieldValueDTO> fieldValues = values.stream().map(fv -> {
            FieldDefinitionEntity field = fieldDefinitionRepository.findById(fv.getFieldDefinitionId())
                    .orElse(null);
            
            String label = field != null ? field.getLabel() : "Campo desconocido";
            String type = field != null ? field.getType().name() : "TEXT";
            
            // Lógica para detectar si es un archivo
            boolean isFile = type.equals("PDF") || type.equals("JPG") || type.equals("FILE");
            
            return new FieldValueDTO(label, fv.getValue(), isFile, type);
        }).collect(Collectors.toList());

        // 4. Construimos la respuesta usando los datos que ya están en el objeto 'submission'
        return ResponseEntity.ok(new SubmissionDetailResponse(
            submission.getId(),
            submission.getCandidateName(), // Ya disponible en el dominio
            submission.getEmail(),         // Ya disponible en el dominio
            submission.getStatus().name(),
            fieldValues
        ));
    }

    public record SubmissionDetailResponse(
        UUID id,
        String candidateName,
        String email,
        String status,
        List<FieldValueDTO> fields
    ) {}

    public record FieldValueDTO(
        String label,
        String value,
        boolean isFile,
        String type
    ) {}
}