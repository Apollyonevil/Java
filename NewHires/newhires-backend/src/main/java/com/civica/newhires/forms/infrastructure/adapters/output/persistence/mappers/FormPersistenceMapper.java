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

    public FieldDefinition toDomain(FieldDefinitionEntity entity) {
        if (entity == null) return null;
        // Se añade entity.getSortOrder() como 7º parámetro del constructor
        return new FieldDefinition(
            entity.getId(), 
            entity.getLabel(), 
            entity.getType(),
            entity.isRequired(), 
            entity.getPlaceholder(), 
            entity.getOptions(),
            entity.getSortOrder() // <--- IMPORTANTE: Para que el dominio tenga el orden
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
        entity.setSortOrder(domain.getSortOrder()); 
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

    public Submission toDomain(SubmissionEntity entity) {
        if (entity == null) return null;
        return new Submission(
            entity.getId(),
            entity.getEmployeeId(),
            entity.getCandidateName(), 
            entity.getEmail(),
            entity.getToken(),
            entity.getSubmittedAt(),
            entity.getStatus()
        );
    }

    public SubmissionEntity toEntity(Submission domain) {
        if (domain == null) return null;
        SubmissionEntity entity = new SubmissionEntity();
        entity.setId(domain.getId());
        entity.setEmployeeId(domain.getEmployeeId());
        entity.setCandidateName(domain.getCandidateName());
        entity.setEmail(domain.getEmail()); 
        entity.setToken(domain.getToken());
        entity.setSubmittedAt(domain.getSubmittedAt());
        entity.setStatus(domain.getStatus());
        return entity;
    }
}