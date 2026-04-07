package com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "field_values")
@Getter @Setter
public class FieldValueEntity {
    @Id
    private UUID id;

    private UUID fieldDefinitionId;
    private UUID employeeId;

    private UUID submissionId;
    private UUID fileResourceId;

    @Column(columnDefinition = "TEXT") 
    private String value;
}