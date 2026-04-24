package com.civica.newhires.candidates.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@Getter
@Setter
public class CandidateEntity {

    @Id
    @Column(name = "id", length = 36)
    private UUID id; // Asignación manual vía UUID

    @Column(name = "candidate_name", nullable = false)
    private String candidateName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public CandidateEntity() {}
}