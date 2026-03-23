package com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "candidates")
public class CandidateEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id", length = 36, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(name = "candidate_name")
    private String candidateName;

    @Column(name = "email")
    private String email;

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "employee_id", length = 36, columnDefinition = "VARCHAR(36)")
    private UUID employeeId;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
}