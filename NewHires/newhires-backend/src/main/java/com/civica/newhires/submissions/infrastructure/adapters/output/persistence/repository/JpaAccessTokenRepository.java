package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAccessTokenRepository extends JpaRepository<AccessTokenEntity, Long> {
    
    Optional<AccessTokenEntity> findByToken(String token);

    // Nota el "_" para indicar que busque dentro del objeto submission el campo id
    Optional<AccessTokenEntity> findFirstBySubmission_IdAndUsedFalseOrderByExpiresAtDesc(UUID submissionId);

    List<AccessTokenEntity> findAllBySubmission_Id(UUID submissionId);

    @Modifying
    @Query("UPDATE AccessTokenEntity a SET a.used = true WHERE a.submission.id = :submissionId")
    void invalidateAllBySubmissionId(@Param("submissionId") UUID submissionId);
}