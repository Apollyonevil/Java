package com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;

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
        entity.setOptions(domain.getOptions());
        entity.setSortOrder(domain.getSortOrder());
        entity.setActive(domain.isActive());
        return entity;
    }

    public FieldValueEntity toEntity(FieldValue domain) {
        if (domain == null) return null;
        FieldValueEntity entity = new FieldValueEntity();
        entity.setId(domain.getId());
        entity.setFieldDefinitionId(domain.getFieldDefinitionId());
        entity.setEmployeeId(domain.getEmployeeId());
        entity.setSubmissionId(domain.getSubmissionId());
        entity.setValue(domain.getValue());
        entity.setFileResourceId(domain.getFileResourceId());
        return entity;
    }

    public Submission toDomain(SubmissionEntity entity) {
        if (entity == null) return null;
        CandidateEntity candidate = entity.getCandidate();
        return new Submission(
            entity.getId(),
            candidate != null ? candidate.getId() : null,
            candidate != null ? candidate.getEmployeeId() : null,
            entity.getSubmittedAt(),
            entity.getStatus()
        );
    }

    public SubmissionEntity toEntity(Submission domain, CandidateEntity candidate) {
        if (domain == null) return null;
        SubmissionEntity entity = new SubmissionEntity();
        entity.setId(domain.getId());
        entity.setCandidate(candidate);
        entity.setSubmittedAt(domain.getSubmittedAt());
        entity.setStatus(domain.getStatus());
        return entity;
    }
}