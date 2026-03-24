package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.CandidateEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaCandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    Optional<CandidateEntity> findByEmail(String email);
}