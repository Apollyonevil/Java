package com.civica.newhires.submissions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Submission {
    private final UUID id;
    private final UUID employeeId; 
    private final String candidateName;
    private final String email;
    private final LocalDateTime submittedAt;
    private LocalDateTime expiresAt;
    private String token;
    private SubmissionStatus status;

    // 1. Constructor para NUEVAS invitaciones
    public Submission(UUID employeeId, String candidateName, String email, String token) {
        this.id = UUID.randomUUID(); 
        this.employeeId = (employeeId != null) ? employeeId : UUID.randomUUID();
        this.candidateName = candidateName;
        this.email = email;
        this.token = token;
        this.submittedAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusHours(48); 
        this.status = SubmissionStatus.PENDING_INVITE; 
    }

    // 2. Constructor para RECONSTRUCCIÓN (El que necesita el Mapper)
    public Submission(UUID id, UUID employeeId, String candidateName, String email, 
                      String token, LocalDateTime submittedAt, LocalDateTime expiresAt, 
                      SubmissionStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
        this.token = token;
        this.submittedAt = submittedAt;
        this.expiresAt = expiresAt;
        this.status = status;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getEmployeeId() { return employeeId; }
    public String getCandidateName() { return candidateName; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public SubmissionStatus getStatus() { return status; }

    // Setters
    public void setToken(String token) { this.token = token; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setStatus(SubmissionStatus status) { this.status = status; }

    public boolean isTokenExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }
}