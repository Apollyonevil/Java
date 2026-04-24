package com.civica.newhires.submissions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class SubmissionStatusHistory {
    private final UUID id;
    private final UUID submissionId;
    private final SubmissionStatus status;
    private final LocalDateTime changedAt;
    private final String changedBy;

    public SubmissionStatusHistory(UUID submissionId, SubmissionStatus status, String changedBy) {
        this.id = UUID.randomUUID();
        this.submissionId = submissionId;
        this.status = status;
        this.changedAt = LocalDateTime.now();
        this.changedBy = changedBy;
    }

    public SubmissionStatusHistory(UUID id, UUID submissionId, SubmissionStatus status,
                                   LocalDateTime changedAt, String changedBy) {
        this.id = id;
        this.submissionId = submissionId;
        this.status = status;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
    }

    public UUID getId() { return id; }
    public UUID getSubmissionId() { return submissionId; }
    public SubmissionStatus getStatus() { return status; }
    public LocalDateTime getChangedAt() { return changedAt; }
    public String getChangedBy() { return changedBy; }
}