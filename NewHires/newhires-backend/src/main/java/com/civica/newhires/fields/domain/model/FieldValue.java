package com.civica.newhires.fields.domain.model;

import java.util.UUID;

public class FieldValue {
    private final UUID id;
    private final UUID fieldDefinitionId;
    private final UUID employeeId;
    private final UUID submissionId;
    private final String value;
    private final UUID fileResourceId;

    public FieldValue(UUID fieldDefinitionId, UUID employeeId, UUID submissionId, String value) {
        this.id = UUID.randomUUID();
        this.fieldDefinitionId = fieldDefinitionId;
        this.employeeId = employeeId;
        this.submissionId = submissionId;
        this.value = value;
        this.fileResourceId = null;
    }

    public FieldValue(UUID fieldDefinitionId, UUID employeeId, UUID submissionId,
                      String value, UUID fileResourceId) {
        this.id = UUID.randomUUID();
        this.fieldDefinitionId = fieldDefinitionId;
        this.employeeId = employeeId;
        this.submissionId = submissionId;
        this.value = value;
        this.fileResourceId = fileResourceId;
    }

    public FieldValue(UUID id, UUID fieldDefinitionId, UUID employeeId,
                      UUID submissionId, String value, UUID fileResourceId) {
        this.id = id;
        this.fieldDefinitionId = fieldDefinitionId;
        this.employeeId = employeeId;
        this.submissionId = submissionId;
        this.value = value;
        this.fileResourceId = fileResourceId;
    }

    public UUID getId() { return id; }
    public UUID getFieldDefinitionId() { return fieldDefinitionId; }
    public UUID getEmployeeId() { return employeeId; }
    public UUID getSubmissionId() { return submissionId; }
    public String getValue() { return value; }
    public UUID getFileResourceId() { return fileResourceId; }
}