package com.civica.newhires.submissions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Submission {
    private final UUID id;
    private final UUID employeeId; 
    private final String candidateName;
    private final String email;
    private final LocalDateTime createdAt;
    private LocalDateTime submittedAt;
    private SubmissionStatus status;

    private String token;
    private LocalDateTime expiresAt;

  
    public Submission(UUID employeeId, String candidateName, String email) {
        this.id = UUID.randomUUID(); // Este será el ID definitivo
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.status = SubmissionStatus.PENDING_INVITE; 
    }

 
    public Submission(UUID id, UUID employeeId, String candidateName, String email, 
                      LocalDateTime createdAt, LocalDateTime submittedAt, 
                      SubmissionStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
        this.createdAt = createdAt;
        this.submittedAt = submittedAt;
        this.status = status;
    }

    // --- GETTERS ---
    public UUID getId() { return id; }
    public UUID getEmployeeId() { return employeeId; }
    public String getCandidateName() { return candidateName; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public SubmissionStatus getStatus() { return status; }
    public String getToken() { return token; }
    public LocalDateTime getExpiresAt() { return expiresAt; }

    // --- SETTERS ---
    public void setToken(String token) { this.token = token; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setStatus(SubmissionStatus status) { this.status = status; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public void markAsSubmitted() {
        this.status = SubmissionStatus.SUBMITTED;
        this.submittedAt = LocalDateTime.now();
    }
}