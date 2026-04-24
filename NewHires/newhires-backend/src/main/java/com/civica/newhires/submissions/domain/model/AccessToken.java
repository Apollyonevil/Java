package com.civica.newhires.submissions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class AccessToken {
    private final UUID id;
    private final UUID submissionId;
    private final String token;
    private final LocalDateTime expiresAt;
    private boolean used;

    public AccessToken(UUID submissionId) {
        this.id = UUID.randomUUID();
        this.submissionId = submissionId;
        this.token = UUID.randomUUID().toString();
        this.expiresAt = LocalDateTime.now().plusHours(48);
        this.used = false;
    }

    public AccessToken(UUID id, UUID submissionId, String token,
                       LocalDateTime expiresAt, boolean used) {
        this.id = id;
        this.submissionId = submissionId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    public void markAsUsed() { this.used = true; }

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !used && !isExpired();
    }

    public UUID getId() { return id; }
    public UUID getSubmissionId() { return submissionId; }
    public String getToken() { return token; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isUsed() { return used; }
}