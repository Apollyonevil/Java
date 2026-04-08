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
    @Transactional
    public AccessToken save(AccessToken accessToken) {
        SubmissionEntity submission = submissionRepo.findById(accessToken.getSubmissionId())
                .orElseThrow(() -> new RuntimeException("Submission no encontrada: " + accessToken.getSubmissionId()));

        AccessTokenEntity entity = accessTokenRepo.findByToken(accessToken.getToken())
                .orElse(new AccessTokenEntity());
        
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
        // Llamada corregida con el nombre exacto del método del repo
        return accessTokenRepo.findFirstBySubmission_IdAndUsedFalseOrderByExpiresAtDesc(submissionId)
                .map(this::toDomain);
    }

    @Override
    public List<AccessToken> findAllBySubmissionId(UUID submissionId) {
        // Llamada corregida con el nombre exacto del método del repo
        return accessTokenRepo.findAllBySubmission_Id(submissionId)
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
        // Ahora el constructor de AccessToken coincide con estos parámetros
        return new AccessToken(
            entity.getSubmission().getId(),
            entity.getToken(),
            entity.getExpiresAt(),
            entity.isUsed()
        );
    }
}