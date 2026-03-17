package com.civica.newhires.forms.application.mapper;

import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.domain.model.FieldValue;
import java.util.UUID;

public class FormMapper {
    public static FieldValue toDomain(FieldResponseDTO dto, UUID employeeId) {
        return new FieldValue(dto.fieldDefinitionId(), employeeId, dto.value());
    }
}