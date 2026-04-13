package com.civica.newhires.candidates.domain.ports.output;

import com.civica.newhires.candidates.domain.model.AccessToken;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AccessTokenPort {
    AccessToken save(AccessToken accessToken);
    Optional<AccessToken> findByToken(String token);
    Optional<AccessToken> findValidBySubmissionId(UUID submissionId);
    List<AccessToken> findAllBySubmissionId(UUID submissionId);
    void invalidateAllBySubmissionId(UUID submissionId);
    void deleteBySubmissionId(UUID submissionId); 
}