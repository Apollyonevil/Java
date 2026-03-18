package com.civica.newhires.forms.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Submission {
    private final UUID id;
    private final UUID employeeId;
    private final String candidateName; 
    private final String email;
    private final LocalDateTime submittedAt;
    private String token;            // <--- AÑADIDO (Para el link de invitación)
    private SubmissionStatus status; // <--- QUITADO EL FINAL (Para poder cambiar de PENDING a COMPLETED)

    // 1. Constructor para NUEVAS INVITACIONES (El que usará el Admin)
    public Submission(UUID employeeId, String candidateName, String email, String token) {
        this.id = UUID.randomUUID();
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
        this.token = token;
        this.submittedAt = LocalDateTime.now();
        this.status = SubmissionStatus.PENDING_INVITE; // Asegúrate de tener este ENUM
    }

    // 2. Constructor completo (Para que el Repositorio cargue de la DB)
    public Submission(UUID id, UUID employeeId, String candidateName, String email, 
                      String token, LocalDateTime submittedAt, SubmissionStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
        this.token = token;
        this.submittedAt = submittedAt;
        this.status = status;
    }

    // SETTERS necesarios para campos que cambian
    public void setStatus(SubmissionStatus status) { this.status = status; }
    public void setToken(String token) { this.token = token; }

    // GETTERS
    public String getEmail() { return email; }
    public String getCandidateName() { return candidateName; }
    public UUID getId() { return id; }
    public UUID getEmployeeId() { return employeeId; }
    public String getToken() { return token; } // <--- IMPORTANTE para el Admin
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public SubmissionStatus getStatus() { return status; }
}