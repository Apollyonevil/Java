package com.civica.newhires.auth.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Invitation {
    private final UUID id;
    private final String email;
    private final String token;
    private final LocalDateTime expiresAt;
    private InvitationStatus status;

    
    private static final int EXPIRATION_HOURS = 48;

 
    public Invitation(String email) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.token = UUID.randomUUID().toString(); // Generamos un token único y seguro
        this.expiresAt = LocalDateTime.now().plusHours(EXPIRATION_HOURS);
        this.status = InvitationStatus.PENDING;
    }


    public Invitation(UUID id, String email, String token, LocalDateTime expiresAt, InvitationStatus status) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.expiresAt = expiresAt;
        this.status = status;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isPending() {
        return this.status == InvitationStatus.PENDING;
    }


    public boolean isValid() {
        return isPending() && !isExpired();
    }


    public void markAsUsed() {
        if (!isValid()) {
            throw new IllegalStateException("No se puede usar una invitación expirada o ya procesada");
        }
        this.status = InvitationStatus.USED;
    }


    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public InvitationStatus getStatus() { return status; }
}