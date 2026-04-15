package com.civica.newhires.fields.infrastructure.adapters.input.controllers.web.mappers;

import com.civica.newhires.fields.application.dto.FieldDefinitionDTO;
import com.civica.newhires.fields.domain.model.FieldDefinition;
import org.springframework.stereotype.Component;

@Component 
public class FieldDTOMapper {

    public FieldDefinitionDTO toDTO(FieldDefinition domain) {
        if (domain == null) return null;

        return new FieldDefinitionDTO(
            domain.getId(),
            domain.getLabel(),
            domain.getType().name(),
            domain.isRequired(),
            domain.getPlaceholder(),
            domain.getOptions(),   
            domain.getSortOrder(),
            domain.isActive()   
        );
    }
}