package com.civica.newhires.submissions.infrastructure.adapters.output.persistence;

import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.repository.JpaAccessTokenRepository;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.repository.JpaCandidateRepository;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFormVersionRepository;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.*;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; 
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j 
@Component
@RequiredArgsConstructor
public class SubmissionPersistenceAdapter implements SubmissionPort {

    private final JpaSubmissionRepository submissionRepo;
    private final JpaCandidateRepository candidateRepo;
    private final JpaAccessTokenRepository tokenRepo;
    private final JpaFormVersionRepository versionRepo;
    private final FormPersistenceMapper mapper;


@Override
@Transactional
public Submission save(Submission submission) {
    CandidateEntity candidate = candidateRepo.findByEmail(submission.getEmail())
            .orElseGet(() -> {
                CandidateEntity newCandidate = new CandidateEntity();
                newCandidate.setId(UUID.randomUUID());
                newCandidate.setCandidateName(submission.getCandidateName());
                newCandidate.setEmail(submission.getEmail());
                return candidateRepo.saveAndFlush(newCandidate);
            });

    UUID activeVersionId = versionRepo.findByActiveTrue()
            .map(v -> v.getId())
            .orElseThrow(() -> new RuntimeException("No hay versión activa"));

    SubmissionEntity entity = new SubmissionEntity();

    entity.setId(submission.getId()); 
    entity.setCandidate(candidate);
    entity.setEmployeeId(submission.getEmployeeId());
    entity.setCreatedAt(submission.getCreatedAt() != null ? submission.getCreatedAt() : LocalDateTime.now());
    entity.setVersionId(activeVersionId);
    entity.setStatus(submission.getStatus());

    SubmissionEntity savedEntity = submissionRepo.saveAndFlush(entity);
    return mapper.toDomain(savedEntity);
}

    @Override 
    public java.util.Optional<Submission> findById(UUID id) { 
        return submissionRepo.findById(id).map(mapper::toDomain); 
    }

    @Override 
    public java.util.Optional<Submission> findByToken(String token) { 
        return tokenRepo.findByToken(token)
                .map(AccessTokenEntity::getSubmission)
                .map(mapper::toDomain); 
    }

    @Override 
    public java.util.Optional<Submission> findByEmployeeId(UUID employeeId) { 
        return submissionRepo.findByCandidate_Id(employeeId).map(mapper::toDomain); 
    }

    @Override 
    public java.util.List<Submission> findAll() { 
        return submissionRepo.findAll().stream().map(mapper::toDomain).toList(); 
    }
    
    @Override 
    @Transactional
    public void deleteById(UUID id) { 
        tokenRepo.invalidateAllBySubmissionId(id); 
        submissionRepo.deleteById(id); 
    }
}