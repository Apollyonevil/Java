package com.civica.newhires.fields.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.model.FieldValue;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class FormPersistenceMapper {

    // --- MAPPING DE DEFINICIÓN DE CAMPOS ---

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
            entity.isActive() // Si da error aquí, cámbialo a entity.active o entity.getActive()
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
        
        // CORRECCIÓN CRÍTICA: Aseguramos que las opciones se guarden siempre
        if (domain.getOptions() != null) {
            entity.setOptions(new ArrayList<>(domain.getOptions()));
        } else {
            entity.setOptions(new ArrayList<>());
        }
        
        entity.setSortOrder(domain.getSortOrder());
        entity.setActive(domain.isActive());
        return entity;
    }

    // --- MAPPING DE VALORES DE RESPUESTA ---

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

    // --- MAPPING DE SUBMISSION ---

    public Submission toDomain(SubmissionEntity entity) {
        if (entity == null) return null;
        CandidateEntity candidate = entity.getCandidate();
        
        return new Submission(
            entity.getId(),
            entity.getEmployeeId(), 
            candidate != null ? candidate.getCandidateName() : "Candidato Desconocido",
            candidate != null ? candidate.getEmail() : "Sin Email",
            entity.getToken(),
            entity.getSubmittedAt(), 
            entity.getExpiresAt(),
            entity.getStatus()      
        );
    }

    public SubmissionEntity toEntity(Submission domain, CandidateEntity candidateEntity) {
        if (domain == null) return null;
        SubmissionEntity entity = new SubmissionEntity();
        entity.setId(domain.getId());
        entity.setCandidate(candidateEntity);
        entity.setEmployeeId(domain.getEmployeeId()); 
        entity.setToken(domain.getToken());
        entity.setSubmittedAt(domain.getSubmittedAt());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setStatus(domain.getStatus());
        
        return entity;
    }
}