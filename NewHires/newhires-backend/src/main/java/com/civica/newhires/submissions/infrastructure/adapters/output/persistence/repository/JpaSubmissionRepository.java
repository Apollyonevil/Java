package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface JpaSubmissionRepository extends JpaRepository<SubmissionEntity, UUID> {
    // Este método es el que permite que el validador encuentre el token
    Optional<SubmissionEntity> findByToken(String token);
    
    Optional<SubmissionEntity> findByCandidate_Id(UUID candidateId);
}