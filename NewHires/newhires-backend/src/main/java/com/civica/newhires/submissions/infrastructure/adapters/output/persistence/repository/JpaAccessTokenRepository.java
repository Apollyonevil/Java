package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAccessTokenRepository extends JpaRepository<AccessTokenEntity, UUID> {

    Optional<AccessTokenEntity> findByToken(String token);

    List<AccessTokenEntity> findAllBySubmissionId(UUID submissionId);

    Optional<AccessTokenEntity> findFirstBySubmissionIdAndUsedFalseOrderByExpiresAtDesc(UUID submissionId);

    @Modifying
    @Query("UPDATE AccessTokenEntity a SET a.used = true WHERE a.submission.id = :submissionId")
    void invalidateAllBySubmissionId(@Param("submissionId") UUID submissionId);
}