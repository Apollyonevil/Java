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

    @Column(columnDefinition = "TEXT") 
    private String value;
}