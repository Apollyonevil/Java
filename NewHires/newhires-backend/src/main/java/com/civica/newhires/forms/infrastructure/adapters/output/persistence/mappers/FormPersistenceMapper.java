package com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import org.springframework.stereotype.Component;

@Component
public class FormPersistenceMapper {

    public FieldDefinition toDomain(FieldDefinitionEntity entity) {
        if (entity == null) return null;
        return new FieldDefinition(
            entity.getId(),
            entity.getLabel(),
            entity.getType(),
            entity.isRequired(),
            entity.getPlaceholder(),
            entity.getOptions(),
            entity.getSortOrder()
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

    public FieldValueEntity toEntity(FieldValue domain) {
        if (domain == null) return null;
        FieldValueEntity entity = new FieldValueEntity();
        entity.setId(domain.getId());
        entity.setFieldDefinitionId(domain.getFieldDefinitionId());
        entity.setEmployeeId(domain.getEmployeeId());
        entity.setValue(domain.getValue());
        return entity;
    }

    // Ahora toDomain lee los datos del candidato a través de la relación
    public Submission toDomain(SubmissionEntity entity) {
        if (entity == null) return null;
        CandidateEntity candidate = entity.getCandidate();
        return new Submission(
            entity.getId(),
            candidate != null ? candidate.getEmployeeId() : null,
            candidate != null ? candidate.getCandidateName() : null,
            candidate != null ? candidate.getEmail() : null,
            entity.getToken(),
            entity.getSubmittedAt(),
            entity.getExpiresAt(),
            entity.getStatus()
        );
    }

    // toEntity ahora recibe el CandidateEntity ya persistido
    public SubmissionEntity toEntity(Submission domain, CandidateEntity candidate) {
        if (domain == null) return null;
        SubmissionEntity entity = new SubmissionEntity();
        entity.setId(domain.getId());
        entity.setCandidate(candidate);
        entity.setToken(domain.getToken());
        entity.setSubmittedAt(domain.getSubmittedAt());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setStatus(domain.getStatus());
        return entity;
    }
}