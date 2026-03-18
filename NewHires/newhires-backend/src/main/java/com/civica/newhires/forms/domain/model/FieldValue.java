package com.civica.newhires.forms.domain.model;

import java.util.UUID;

public class FieldValue {
    private final UUID id;
    private final UUID fieldDefinitionId;
    private final UUID employeeId;        
    private String value;               

    public FieldValue(UUID fieldDefinitionId, UUID employeeId, String value) {
        this.id = UUID.randomUUID();
        this.fieldDefinitionId = fieldDefinitionId;
        this.employeeId = employeeId;
        this.value = value;
    }

    public FieldValue(UUID id, UUID fieldDefinitionId, UUID employeeId, String value) {
        this.id = id;
        this.fieldDefinitionId = fieldDefinitionId;
        this.employeeId = employeeId;
        this.value = value;
    }

    public void updateValue(String newValue) {
        this.value = newValue;
    }


    public UUID getId() { return id; }
    public UUID getFieldDefinitionId() { return fieldDefinitionId; }
    public UUID getEmployeeId() { return employeeId; }
    public String getValue() { return value; }
}