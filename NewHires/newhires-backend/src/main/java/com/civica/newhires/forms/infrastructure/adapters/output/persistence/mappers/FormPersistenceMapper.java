package com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import org.springframework.stereotype.Component;

@Component
public class FormPersistenceMapper {

    // --- MAPPINGS DE DEFINICIÓN (FieldDefinition) ---
    public FieldDefinition toDomain(FieldDefinitionEntity entity) {
        if (entity == null) return null;
        return new FieldDefinition(
            entity.getId(), entity.getLabel(), entity.getType(),
            entity.isRequired(), entity.getPlaceholder(), entity.getOptions()
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
        entity.setOptions(domain.getOptions());
        return entity;
    }

    // --- MAPPINGS DE VALORES (FieldValue) ---
    public FieldValueEntity toEntity(FieldValue domain) {
        if (domain == null) return null;
        FieldValueEntity entity = new FieldValueEntity();
        entity.setId(domain.getId());
        entity.setFieldDefinitionId(domain.getFieldDefinitionId());
        entity.setEmployeeId(domain.getEmployeeId());
        entity.setValue(domain.getValue());
        return entity;
    }

    // --- MAPPINGS DE ENTREGAS (Submission) ---
    public Submission toDomain(SubmissionEntity entity) {
        if (entity == null) return null;
        return new Submission(
            entity.getId(),
            entity.getEmployeeId(),
            entity.getSubmittedAt(),
            entity.getStatus()
        );
    }

    public SubmissionEntity toEntity(Submission domain) {
        if (domain == null) return null;
        SubmissionEntity entity = new SubmissionEntity();
        entity.setId(domain.getId());
        entity.setEmployeeId(domain.getEmployeeId());
        entity.setSubmittedAt(domain.getSubmittedAt());
        entity.setStatus(domain.getStatus());
        return entity;
    }
}