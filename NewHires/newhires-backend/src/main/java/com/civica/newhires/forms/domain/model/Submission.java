package com.civica.newhires.forms.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Submission {
    private final UUID id;
    private final UUID employeeId;
    private final LocalDateTime submittedAt;
    private SubmissionStatus status;

    // Constructor para nuevas entregas
    public Submission(UUID employeeId) {
        this.id = UUID.randomUUID();
        this.employeeId = employeeId;
        this.submittedAt = LocalDateTime.now();
        this.status = SubmissionStatus.PENDING_REVIEW;
    }

    // Constructor para reconstruir desde DB
    public Submission(UUID id, UUID employeeId, LocalDateTime submittedAt, SubmissionStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.submittedAt = submittedAt;
        this.status = status;
    }

    public UUID getId() { return id; }
    public UUID getEmployeeId() { return employeeId; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public SubmissionStatus getStatus() { return status; }
}