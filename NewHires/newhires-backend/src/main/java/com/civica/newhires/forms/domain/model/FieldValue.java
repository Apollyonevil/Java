package com.civica.newhires.forms.domain.model;

import java.util.UUID;

public class FieldValue {
    private final UUID id;
    private final UUID fieldDefinitionId; // Relación con la pregunta
    private final UUID employeeId;        // Quién responde
    private String value;                 // El dato (texto o ruta del archivo)

    public FieldValue(UUID fieldDefinitionId, UUID employeeId, String value) {
        this.id = UUID.randomUUID();
        this.fieldDefinitionId = fieldDefinitionId;
        this.employeeId = employeeId;
        this.value = value;
    }

    // Constructor para persistencia
    public FieldValue(UUID id, UUID fieldDefinitionId, UUID employeeId, String value) {
        this.id = id;
        this.fieldDefinitionId = fieldDefinitionId;
        this.employeeId = employeeId;
        this.value = value;
    }

    public void updateValue(String newValue) {
        this.value = newValue;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getFieldDefinitionId() { return fieldDefinitionId; }
    public UUID getEmployeeId() { return employeeId; }
    public String getValue() { return value; }
}