package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface JpaSubmissionRepository extends JpaRepository<SubmissionEntity, UUID> {

    Optional<SubmissionEntity> findByCandidate_Id(UUID candidateId);
}