package com.civica.newhires.auth.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;



public class Invitation {
    private final UUID id;
    private final String email;
    private final String token;
    private final LocalDateTime expiresAt;
    private InvitationStatus status;


    public Invitation(UUID id, String email, String token, LocalDateTime expiresAt, InvitationStatus status) {
        this.id = id != null ? id : UUID.randomUUID();
        this.email = email;
        this.token = token != null ? token : UUID.randomUUID().toString();
        this.expiresAt = expiresAt != null ? expiresAt : LocalDateTime.now().plusHours(48);
        this.status = status != null ? status : InvitationStatus.PENDING;
    }

    public Invitation(String email) {
        this(
            UUID.randomUUID(), 
            email, 
            UUID.randomUUID().toString(), 
            LocalDateTime.now().plusHours(48), 
            InvitationStatus.PENDING
        );
    }


    public boolean isValid() {
        return status == InvitationStatus.PENDING && LocalDateTime.now().isBefore(expiresAt);
    }


    public void accept() {
        this.status = InvitationStatus.ACCEPTED; 
    }


    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public InvitationStatus getStatus() { return status; }
}