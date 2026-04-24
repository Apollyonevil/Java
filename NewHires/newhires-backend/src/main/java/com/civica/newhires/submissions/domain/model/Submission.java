package com.civica.newhires.submissions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Submission {
    private final UUID id;
    private final UUID candidateId;
    private final UUID employeeId;
    private final LocalDateTime submittedAt;
    private SubmissionStatus status;

    public Submission(UUID candidateId, UUID employeeId) {
        this.id = UUID.randomUUID();
        this.candidateId = candidateId;
        this.employeeId = employeeId;
        this.submittedAt = LocalDateTime.now();
        this.status = SubmissionStatus.PENDING_INVITE;
    }

    public Submission(UUID id, UUID candidateId, UUID employeeId,
                      LocalDateTime submittedAt, SubmissionStatus status) {
        this.id = id;
        this.candidateId = candidateId;
        this.employeeId = employeeId;
        this.submittedAt = submittedAt;
        this.status = status;
    }

    public void setStatus(SubmissionStatus status) { this.status = status; }

    public UUID getId() { return id; }
    public UUID getCandidateId() { return candidateId; }
    public UUID getEmployeeId() { return employeeId; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public SubmissionStatus getStatus() { return status; }
}