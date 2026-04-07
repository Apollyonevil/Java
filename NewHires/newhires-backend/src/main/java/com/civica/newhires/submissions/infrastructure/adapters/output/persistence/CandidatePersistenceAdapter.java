package com.civica.newhires.submissions.infrastructure.adapters.output.persistence;

import com.civica.newhires.submissions.domain.model.Candidate;
import com.civica.newhires.submissions.domain.ports.output.CandidateRepository;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository.JpaCandidateRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CandidatePersistenceAdapter implements CandidateRepository {

    private final JpaCandidateRepository candidateRepo;

    @Override
    public Candidate save(Candidate candidate) {
        CandidateEntity entity = toEntity(candidate);
        CandidateEntity saved = candidateRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Candidate> findById(UUID id) {
        return candidateRepo.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Candidate> findByEmail(String email) {
        return candidateRepo.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<Candidate> findAll() {
        return candidateRepo.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        candidateRepo.deleteById(id);
    }

    private CandidateEntity toEntity(Candidate candidate) {
        CandidateEntity entity = new CandidateEntity();
        entity.setId(candidate.getId());
        entity.setEmployeeId(candidate.getEmployeeId());
        entity.setCandidateName(candidate.getCandidateName());
        entity.setEmail(candidate.getEmail());
        return entity;
    }

    private Candidate toDomain(CandidateEntity entity) {
        return new Candidate(
            entity.getId(),
            entity.getEmployeeId(),
            entity.getCandidateName(),
            entity.getEmail()
        );
    }
}