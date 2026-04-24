package com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "field_values")
@Getter @Setter
public class FieldValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(name = "field_definition_id", nullable = false)
    private UUID fieldDefinitionId;

    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "submission_id", nullable = false)
    private UUID submissionId;

    @Column(name = "file_resource_id") 
    private UUID fileResourceId;

    @Column(name = "value", columnDefinition = "TEXT") 
    private String value;
}