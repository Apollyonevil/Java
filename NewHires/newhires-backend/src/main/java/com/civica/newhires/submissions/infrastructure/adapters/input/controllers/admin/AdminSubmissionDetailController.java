package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository.JpaFieldValueRepository;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;

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

    private final SubmissionPort submissionRepository;
    private final JpaFieldValueRepository fieldValueRepository;
    private final JpaFieldDefinitionRepository fieldDefinitionRepository;

    @GetMapping("/submissions/{id}/detail")
    public ResponseEntity<SubmissionDetailResponse> getDetail(@PathVariable UUID id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        List<FieldValueEntity> values = fieldValueRepository.findByEmployeeId(submission.getEmployeeId());

        List<FieldValueDTO> fieldValues = values.stream().map(fv -> {
            FieldDefinitionEntity field = fieldDefinitionRepository.findById(fv.getFieldDefinitionId())
                    .orElse(null);

            String label = field != null ? field.getLabel() : "Campo desconocido";
            String type = field != null ? field.getType().name() : "TEXT";
            boolean isFile = type.equals("PDF") || type.equals("JPG");

            // Si es archivo, devolvemos el fileResourceId para que el front construya
            // la URL de descarga: /api/documents/download/{fileResourceId}
            String value = isFile
                    ? (fv.getFileResourceId() != null ? fv.getFileResourceId().toString() : null)
                    : fv.getValue();

            return new FieldValueDTO(label, value, isFile, type);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new SubmissionDetailResponse(
                submission.getCandidateName(),
                submission.getEmail(),
                submission.getStatus().name(),
                fieldValues
        ));
    }

    public record SubmissionDetailResponse(
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