package com.civica.newhires.fields.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;
import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.model.FieldValue;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;

import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
        
        if (domain.getOptions() != null) {
            entity.setOptions(new ArrayList<>(domain.getOptions()));
        } else {
            entity.setOptions(new ArrayList<>());
        }
        
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


    public Submission toDomain(SubmissionEntity entity) {
        if (entity == null) return null;
        
        Submission submission = new Submission(
            entity.getId(),
            entity.getEmployeeId(), 
            entity.getCandidate() != null ? entity.getCandidate().getCandidateName() : "Candidato Desconocido",
            entity.getCandidate() != null ? entity.getCandidate().getEmail() : "Sin Email",
            entity.getCreatedAt(),
            entity.getSubmittedAt(), 
            entity.getStatus()      
        );

        if (entity.getAccessToken() != null) {
            submission.setToken(entity.getAccessToken() != null ? entity.getAccessToken().getToken() : null);
            submission.setExpiresAt(entity.getAccessToken().getExpiresAt());
        }

        return submission;
    }

    public SubmissionEntity toEntity(Submission domain, CandidateEntity candidateEntity) {
        if (domain == null) return null;
        SubmissionEntity entity = new SubmissionEntity();
        
        entity.setId(domain.getId());
        entity.setCandidate(candidateEntity);
        entity.setEmployeeId(domain.getEmployeeId()); 
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setSubmittedAt(domain.getSubmittedAt());
        entity.setStatus(domain.getStatus());
        
        return entity;
    }
}