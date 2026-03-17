package com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities;

import com.civica.newhires.forms.domain.model.SubmissionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "submissions")
@Getter @Setter
public class SubmissionEntity {
    @Id
    private UUID id;
    private UUID employeeId;
    private LocalDateTime submittedAt;
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
}