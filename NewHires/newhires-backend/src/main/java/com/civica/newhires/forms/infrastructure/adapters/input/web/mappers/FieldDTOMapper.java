package com.civica.newhires.forms.infrastructure.adapters.input.web.mappers;

import com.civica.newhires.forms.application.dto.FieldDefinitionDTO;
import com.civica.newhires.forms.domain.model.FieldDefinition;
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
            domain.getSortOrder()   
        );
    }
}