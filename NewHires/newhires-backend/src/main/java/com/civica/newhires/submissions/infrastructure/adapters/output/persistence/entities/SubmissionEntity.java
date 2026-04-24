package com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Column(name = "employee_id")
    private UUID employeeId;

    // Cambiado a UUID para coincidir con el tipo de columna en la DB
    @Column(name = "version_id")
    private UUID versionId;

    @Column(name = "token")
    private String token;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private com.civica.newhires.submissions.domain.model.SubmissionStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.versionId == null) {
            // Generamos un UUID aleatorio para cumplir con la restricción de la DB
            this.versionId = UUID.randomUUID();
        }
    }
}