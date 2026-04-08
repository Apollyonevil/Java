package com.civica.newhires.fields.application.mapper;

import com.civica.newhires.fields.application.dto.FieldResponseDTO;
import com.civica.newhires.fields.domain.model.FieldValue;
import java.util.UUID;

public class FormMapper {
    public static FieldValue toDomain(FieldResponseDTO dto, UUID employeeId, UUID submissionId) {
        return new FieldValue(
            UUID.fromString(dto.fieldDefinitionId().toString()),
            employeeId,
            submissionId,
            dto.value()
        );
    }
}