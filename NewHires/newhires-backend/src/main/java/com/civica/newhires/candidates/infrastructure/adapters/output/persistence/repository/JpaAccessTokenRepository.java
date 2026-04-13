package com.civica.newhires.candidates.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAccessTokenRepository extends JpaRepository<AccessTokenEntity, Long> {
    
    Optional<AccessTokenEntity> findByToken(String token);

    // 1. Para buscar el token válido (sustituye al nombre largo que fallaba)
    @Query("SELECT a FROM AccessTokenEntity a WHERE a.submission.id = :submissionId AND a.used = false ORDER BY a.expiresAt DESC")
    List<AccessTokenEntity> findValidTokens(@Param("submissionId") UUID submissionId);

    // 2. Para buscar todos los tokens de una submission
    @Query("SELECT a FROM AccessTokenEntity a WHERE a.submission.id = :submissionId")
    List<AccessTokenEntity> findAllBySubmissionId(@Param("submissionId") UUID submissionId);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM AccessTokenEntity a WHERE a.submission.id = :submissionId")
    void deleteBySubmissionId(@Param("submissionId") UUID submissionId);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE AccessTokenEntity a SET a.used = true WHERE a.submission.id = :submissionId")
    void invalidateAllBySubmissionId(@Param("submissionId") UUID submissionId);
}