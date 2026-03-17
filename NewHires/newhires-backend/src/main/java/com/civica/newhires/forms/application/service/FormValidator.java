package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.domain.model.FieldDefinition;
import org.springframework.stereotype.Component;

@Component
public class FormValidator {
    public void validate(FieldDefinition def, FieldResponseDTO dto) {
        if (def.isRequired() && (dto.value() == null || dto.value().isBlank())) {
            throw new RuntimeException("El campo " + def.getLabel() + " es obligatorio.");
        }
    }
}