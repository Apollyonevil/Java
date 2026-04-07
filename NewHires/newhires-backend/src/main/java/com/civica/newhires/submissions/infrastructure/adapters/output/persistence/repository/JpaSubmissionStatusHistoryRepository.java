package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaSubmissionStatusHistoryRepository extends JpaRepository<SubmissionStatusHistoryEntity, UUID> {
    List<SubmissionStatusHistoryEntity> findBySubmissionId(UUID submissionId);
}