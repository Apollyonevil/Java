package com.civica.newhires.candidates.infrastructure.adapters.output.persistence;

import com.civica.newhires.candidates.domain.model.AccessToken;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.repository.JpaAccessTokenRepository;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository.JpaSubmissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccessTokenPersistenceAdapter implements AccessTokenPort {

    private final JpaAccessTokenRepository accessTokenRepo;
    private final JpaSubmissionRepository submissionRepo;

@Override
@Transactional
public AccessToken save(AccessToken accessToken) {
    SubmissionEntity submission = submissionRepo.findById(accessToken.getSubmissionId())
            .orElseThrow(() -> new RuntimeException("Submission no encontrada: " + accessToken.getSubmissionId()));

    // Buscar si ya existe para hacer UPDATE, si no existe hacer INSERT
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
    @Transactional
    public void deleteBySubmissionId(UUID submissionId) {
        // Este es el método que acabamos de añadir al JpaAccessTokenRepository con @Query
        accessTokenRepo.deleteBySubmissionId(submissionId);
    }

    @Override
    public Optional<AccessToken> findValidBySubmissionId(UUID submissionId) {
        return accessTokenRepo.findValidTokens(submissionId)
                .stream()
                .findFirst() // Cogemos el primero (el más reciente por el ORDER BY)
                .map(this::toDomain);
    }

        @Override
    public List<AccessToken> findAllBySubmissionId(UUID submissionId) {
        return accessTokenRepo.findAllBySubmissionId(submissionId) // Nombre corregido
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
            entity.getSubmission().getId(),
            entity.getToken(),
            entity.getExpiresAt(),
            entity.isUsed()
        );
    }
}