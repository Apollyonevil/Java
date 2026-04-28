package com.civica.newhires.candidates.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AccessToken {
    private final UUID submissionId;
    private final String token;
    private final LocalDateTime expiresAt;
    private boolean used; 

    public AccessToken(UUID submissionId, String token, LocalDateTime expiresAt, boolean used) {
        this.submissionId = submissionId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = used;
    }


    public AccessToken(UUID submissionId) {
        this.submissionId = submissionId;
        this.token = UUID.randomUUID().toString();
        this.expiresAt = LocalDateTime.now().plusHours(48);
        this.used = false;
    }


    public boolean isValid() {
        return !used && (expiresAt == null || LocalDateTime.now().isBefore(expiresAt));
    }


    public void markAsUsed() {
        this.used = true;
    }
}