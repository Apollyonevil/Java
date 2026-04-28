package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import org.springframework.stereotype.Component;

@Component
public class SubmissionPersistenceMapper {

    public Submission toDomain(SubmissionEntity entity) {
        if (entity == null) return null;

        if (entity.getCandidate() == null) {
            throw new IllegalStateException("Submission sin candidato: " + entity.getId());
        }

        Submission submission = new Submission(
            entity.getId(),
            entity.getEmployeeId(),
            entity.getCandidate().getCandidateName(),
            entity.getCandidate().getEmail(),
            entity.getCreatedAt(),
            entity.getSubmittedAt(),
            entity.getStatus()
        );

        if (entity.getAccessToken() != null) {
            submission.setToken(entity.getAccessToken().getToken());
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