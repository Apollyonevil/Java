package com.civica.newhires.candidates.domain.model;

import java.util.UUID;

public class Candidate {
    private final UUID id;
    private final UUID employeeId;
    private final String candidateName;
    private final String email;

    public Candidate(UUID employeeId, String candidateName, String email) {
        this.id = UUID.randomUUID();
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
    }

    public Candidate(UUID id, UUID employeeId, String candidateName, String email) {
        this.id = id;
        this.employeeId = employeeId;
        this.candidateName = candidateName;
        this.email = email;
    }

    public UUID getId() { return id; }
    public UUID getEmployeeId() { return employeeId; }
    public String getCandidateName() { return candidateName; }
    public String getEmail() { return email; }
}