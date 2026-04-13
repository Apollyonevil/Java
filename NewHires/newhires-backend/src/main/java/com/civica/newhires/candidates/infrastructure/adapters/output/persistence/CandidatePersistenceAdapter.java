package com.civica.newhires.candidates.infrastructure.adapters.output.persistence;

import com.civica.newhires.candidates.domain.model.Candidate;
import com.civica.newhires.candidates.domain.ports.output.CandidatePort;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.repository.JpaCandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CandidatePersistenceAdapter implements CandidatePort {

    private final JpaCandidateRepository candidateRepo;

    @Override
    public Candidate save(Candidate candidate) {
        CandidateEntity entity = new CandidateEntity();
        entity.setId(candidate.getId()); 
        entity.setCandidateName(candidate.getCandidateName());
        entity.setEmail(candidate.getEmail());

        // El flush es vital para que el ID exista antes de crear la submission
        CandidateEntity saved = candidateRepo.saveAndFlush(entity);
        
        return new Candidate(saved.getId(), saved.getCandidateName(), saved.getEmail());
    }

    @Override
    public Optional<Candidate> findByEmail(String email) {
        return candidateRepo.findByEmail(email)
                .map(e -> new Candidate(e.getId(), e.getCandidateName(), e.getEmail()));
    }


    @Override
    public Optional<Candidate> findById(UUID id) {
        return candidateRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<Candidate> findAll() {
        return candidateRepo.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        candidateRepo.deleteById(id);
    }

    private Candidate toDomain(CandidateEntity entity) {
        // CRUCIAL: Usamos entity.getId(), nunca generamos un UUID nuevo aquí
        return new Candidate(
            entity.getId(),
            entity.getCandidateName(),
            entity.getEmail()
        );
    }
}