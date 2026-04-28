package com.civica.newhires.fields.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.model.FieldValue;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FieldPersistenceMapper {

    public FieldDefinition toDomain(FieldDefinitionEntity entity) {
        if (entity == null) return null;
        return new FieldDefinition(
            entity.getId(),
            entity.getLabel(),
            entity.getType(),
            entity.isRequired(),
            entity.getPlaceholder(),
            entity.getFileNamingPrefix(),
            entity.getOptions(),
            entity.getSortOrder(),
            entity.isActive()
        );
    }

    public FieldDefinitionEntity toEntity(FieldDefinition domain) {
        if (domain == null) return null;
        FieldDefinitionEntity entity = new FieldDefinitionEntity();
        entity.setId(domain.getId());
        entity.setLabel(domain.getLabel());
        entity.setType(domain.getType());
        entity.setRequired(domain.isRequired());
        entity.setPlaceholder(domain.getPlaceholder());
        entity.setFileNamingPrefix(domain.getFileNamingPrefix());
        entity.setOptions(domain.getOptions() != null ? new ArrayList<>(domain.getOptions()) : new ArrayList<>());
        entity.setSortOrder(domain.getSortOrder());
        entity.setActive(domain.isActive());
        return entity;
    }

    public FieldValueEntity toEntity(FieldValue domain) {
        if (domain == null) return null;
        FieldValueEntity entity = new FieldValueEntity();
        entity.setFieldDefinitionId(domain.getFieldDefinitionId());
        entity.setEmployeeId(domain.getEmployeeId());
        entity.setSubmissionId(domain.getSubmissionId());
        entity.setValue(domain.getValue());
        entity.setFileResourceId(domain.getFileResourceId());
        return entity;
    }
}