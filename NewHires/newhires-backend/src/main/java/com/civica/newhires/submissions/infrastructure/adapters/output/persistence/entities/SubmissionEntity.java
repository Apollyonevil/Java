package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.CandidateEntity;
import com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities.AccessTokenEntity;

@Entity
@Table(name = "submissions")
@Getter
@Setter
public class SubmissionEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;


    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AccessTokenEntity accessToken;

    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "version_id")
    private UUID versionId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private com.civica.newhires.submissions.domain.model.SubmissionStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.versionId == null) this.versionId = UUID.randomUUID();
    }
}