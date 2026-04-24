package com.civica.newhires.forms.application.mapper;

import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.domain.model.FieldValue;
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