package com.civica.newhires.forms.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Submission {
    private final UUID id;
    private final UUID employeeId;
    private final String candidateName;
    private final String email;
    private final LocalDateTime submittedAt;
    private LocalDateTime expiresAt; // <-- añadido sin final
    private String token;
    private SubmissionStatus status;

    // Constructor para NUEVAS INVITACIONES
    public Submission(UUID employeeId, String candidateName, String email, String token) {
        this.id = UUID.randomUUID();
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
        this.token = token;
        this.submittedAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusHours(48); // <-- caduca en 48h
        this.status = SubmissionStatus.PENDING_INVITE;
    }

    // Constructor completo para cargar de BD
    public Submission(UUID id, UUID employeeId, String candidateName, String email,
                      String token, LocalDateTime submittedAt, LocalDateTime expiresAt, SubmissionStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
        this.token = token;
        this.submittedAt = submittedAt;
        this.expiresAt = expiresAt;
        this.status = status;
    }

    public void setStatus(SubmissionStatus status) { this.status = status; }
    public void setToken(String token) { this.token = token; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public UUID getId() { return id; }
    public UUID getEmployeeId() { return employeeId; }
    public String getCandidateName() { return candidateName; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public SubmissionStatus getStatus() { return status; }

    public boolean isTokenExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }
}