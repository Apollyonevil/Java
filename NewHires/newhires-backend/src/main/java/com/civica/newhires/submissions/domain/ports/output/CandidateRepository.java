package com.civica.newhires.submissions.domain.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.civica.newhires.submissions.domain.model.Candidate;

public interface CandidateRepository {

    Candidate save(Candidate candidate);
    Optional<Candidate> findById(UUID id);
    Optional<Candidate> findByEmail(String email);
    List<Candidate> findAll();
    void deleteById(UUID id);
}