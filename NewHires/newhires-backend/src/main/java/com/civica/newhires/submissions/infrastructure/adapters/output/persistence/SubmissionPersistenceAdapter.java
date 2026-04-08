package com.civica.newhires.submissions.infrastructure.adapters.output.persistence;

import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.repository.JpaAccessTokenRepository;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.repository.JpaCandidateRepository;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFormVersionRepository;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.*;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class SubmissionPersistenceAdapter implements SubmissionRepository {

    private final JpaSubmissionRepository submissionRepo;
    private final JpaCandidateRepository candidateRepo;
    private final JpaAccessTokenRepository tokenRepo;
    private final JpaFormVersionRepository versionRepo; // Tu repositorio existente
    private final FormPersistenceMapper mapper;

    @Override
    @Transactional
    public Submission save(Submission submission) {
        // 1. GESTIÓN DEL CANDIDATO (O creación si no existe)
        CandidateEntity candidate = candidateRepo.findByEmail(submission.getEmail())
                .orElseGet(() -> {
                    CandidateEntity newCandidate = new CandidateEntity();
                    newCandidate.setId(submission.getEmployeeId());
                    newCandidate.setCandidateName(submission.getCandidateName());
                    newCandidate.setEmail(submission.getEmail());
                    return candidateRepo.saveAndFlush(newCandidate);
                });

        // 2. OBTENER LA VERSIÓN ACTIVA (Usando tu método findByActiveTrue)
        UUID activeVersionId = versionRepo.findByActiveTrue()
                .map(v -> v.getId())
                .orElseThrow(() -> new RuntimeException("Error: No hay ninguna versión de formulario marcada como ACTIVA en la tabla form_versions"));

        // 3. GESTIÓN DE LA SUBMISSION (Mapeo a Entidad)
        SubmissionEntity entity = new SubmissionEntity();
        entity.setId(submission.getId());
        entity.setCandidate(candidate);
        
        // Datos de auditoría
        entity.setEmployeeId(submission.getEmployeeId());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setVersionId(activeVersionId); // ID real que cumple la FK Constraint
        
        entity.setToken(submission.getToken());
        entity.setSubmittedAt(submission.getSubmittedAt());
        entity.setExpiresAt(submission.getExpiresAt());
        entity.setStatus(submission.getStatus());

        SubmissionEntity savedEntity = submissionRepo.save(entity);

        // 4. DOBLE PERSISTENCIA: GESTIÓN DEL ACCESS TOKEN
        AccessTokenEntity tokenEntity = tokenRepo.findFirstBySubmission_IdAndUsedFalseOrderByExpiresAtDesc(savedEntity.getId())
                .orElse(new AccessTokenEntity());
        
        tokenEntity.setSubmission(savedEntity);
        tokenEntity.setToken(submission.getToken());
        tokenEntity.setExpiresAt(submission.getExpiresAt());
        tokenEntity.setUsed(false);
        
        tokenRepo.save(tokenEntity);

        return mapper.toDomain(savedEntity);
    }

    @Override public java.util.Optional<Submission> findById(UUID id) { return submissionRepo.findById(id).map(mapper::toDomain); }
    @Override public java.util.Optional<Submission> findByToken(String token) { return submissionRepo.findByToken(token).map(mapper::toDomain); }
    @Override public java.util.Optional<Submission> findByEmployeeId(UUID employeeId) { return submissionRepo.findByCandidate_Id(employeeId).map(mapper::toDomain); }
    @Override public java.util.List<Submission> findAll() { return submissionRepo.findAll().stream().map(mapper::toDomain).toList(); }
    
    @Override 
    @Transactional
    public void deleteById(UUID id) { 
        tokenRepo.invalidateAllBySubmissionId(id); 
        submissionRepo.deleteById(id); 
    }
}