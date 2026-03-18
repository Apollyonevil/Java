package com.civica.newhires.forms.domain.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.exception.BusinessException;

public class FormDomainService {

    public void validateField(FieldDefinition def, String value) {
        if (def.isRequired() && (value == null || value.isBlank())) {
            throw new BusinessException("El campo " + def.getLabel() + " es obligatorio.");
        }
    }

    public String generateFileName(String label, String originalName) {
        String extension = extractExtension(originalName);
        return (label + "_SOLICITANTE_NUEVO" + extension)
                .toUpperCase()
                .replace(" ", "_");
    }

    private String extractExtension(String name) {
        return (name != null && name.contains(".")) 
            ? name.substring(name.lastIndexOf(".")) : "";
    }
}