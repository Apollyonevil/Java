package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import com.civica.newhires.submissions.domain.model.SubmissionStatus;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "submissions")
public class SubmissionEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id", length = 36, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateEntity candidate;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SubmissionStatus status;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccessTokenEntity> accessTokens = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public CandidateEntity getCandidate() { return candidate; }
    public void setCandidate(CandidateEntity candidate) { this.candidate = candidate; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public SubmissionStatus getStatus() { return status; }
    public void setStatus(SubmissionStatus status) { this.status = status; }

    public List<AccessTokenEntity> getAccessTokens() { return accessTokens; }
    public void setAccessTokens(List<AccessTokenEntity> accessTokens) { this.accessTokens = accessTokens; }
}