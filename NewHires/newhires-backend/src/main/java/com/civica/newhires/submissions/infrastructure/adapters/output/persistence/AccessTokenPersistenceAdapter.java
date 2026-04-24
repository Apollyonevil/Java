package com.civica.newhires.submissions.infrastructure.adapters.output.persistence;

import com.civica.newhires.submissions.domain.model.AccessToken;
import com.civica.newhires.submissions.domain.ports.output.AccessTokenRepository;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository.JpaAccessTokenRepository;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository.JpaSubmissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccessTokenPersistenceAdapter implements AccessTokenRepository {

    private final JpaAccessTokenRepository accessTokenRepo;
    private final JpaSubmissionRepository submissionRepo;

    @Override
    public AccessToken save(AccessToken accessToken) {
        SubmissionEntity submission = submissionRepo.findById(accessToken.getSubmissionId())
                .orElseThrow(() -> new RuntimeException("Submission no encontrada: " + accessToken.getSubmissionId()));

        AccessTokenEntity entity = new AccessTokenEntity();
        entity.setId(accessToken.getId());
        entity.setSubmission(submission);
        entity.setToken(accessToken.getToken());
        entity.setExpiresAt(accessToken.getExpiresAt());
        entity.setUsed(accessToken.isUsed());

        AccessTokenEntity saved = accessTokenRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<AccessToken> findByToken(String token) {
        return accessTokenRepo.findByToken(token).map(this::toDomain);
    }

    @Override
    public Optional<AccessToken> findValidBySubmissionId(UUID submissionId) {
        return accessTokenRepo
                .findFirstBySubmissionIdAndUsedFalseOrderByExpiresAtDesc(submissionId)
                .map(this::toDomain);
    }

    @Override
    public List<AccessToken> findAllBySubmissionId(UUID submissionId) {
        return accessTokenRepo.findAllBySubmissionId(submissionId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void invalidateAllBySubmissionId(UUID submissionId) {
        accessTokenRepo.invalidateAllBySubmissionId(submissionId);
    }

    private AccessToken toDomain(AccessTokenEntity entity) {
        return new AccessToken(
            entity.getId(),
            entity.getSubmission().getId(),
            entity.getToken(),
            entity.getExpiresAt(),
            entity.isUsed()
        );
    }
}