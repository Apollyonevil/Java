package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaSubmissionRepository extends JpaRepository<SubmissionEntity, UUID> {
    Optional<SubmissionEntity> findByCandidateEmployeeId(UUID employeeId);
    void deleteById(UUID id);
    Optional<SubmissionEntity> findByToken(String token);
}