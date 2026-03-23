package com.civica.newhires.forms.infrastructure.adapters.output.persistence;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaCandidateRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubmissionPersistenceAdapter implements SubmissionRepository {

    private final JpaSubmissionRepository submissionRepo;
    private final JpaCandidateRepository candidateRepo;
    private final FormPersistenceMapper mapper;

    @Override
    public void save(Submission submission) {
        // Primero guardamos o recuperamos el candidato
        CandidateEntity candidate = candidateRepo.findByEmail(submission.getEmail())
                .orElseGet(() -> {
                    CandidateEntity newCandidate = new CandidateEntity();
                    newCandidate.setId(submission.getEmployeeId());
                    newCandidate.setCandidateName(submission.getCandidateName());
                    newCandidate.setEmail(submission.getEmail());
                    newCandidate.setEmployeeId(submission.getEmployeeId());
                    return candidateRepo.save(newCandidate);
                });

        submissionRepo.save(mapper.toEntity(submission, candidate));
    }

    @Override
    public Optional<Submission> findById(UUID id) {
        return submissionRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Submission> findByEmployeeId(UUID employeeId) {
        return submissionRepo.findByCandidateEmployeeId(employeeId).map(mapper::toDomain);
    }

    @Override
    public List<Submission> findAll() {
        return submissionRepo.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        submissionRepo.deleteById(id);
    }

    @Override
    public Optional<Submission> findByToken(String token) {
        return submissionRepo.findByToken(token).map(mapper::toDomain);
    }
}