package com.civica.newhires.forms.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FormVersion {
    private final UUID id;
    private final Integer versionNumber;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final String description;
    private boolean active;
    private final List<FormVersionField> fields;


    public FormVersion(UUID id, Integer versionNumber, LocalDateTime createdAt,
                       String createdBy, String description, boolean active,
                       List<FormVersionField> fields) {
        this.id = id;
        this.versionNumber = versionNumber;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.description = description;
        this.active = active;
        this.fields = fields;
    }

  
    public static FormVersion create(Integer versionNumber, String createdBy,
                                     String description, List<FormVersionField> fields) {
        return new FormVersion(
            UUID.randomUUID(),
            versionNumber,
            LocalDateTime.now(),
            createdBy,
            description,
            false, 
            fields
        );
    }

    public void activate() { this.active = true; }
    public void deactivate() { this.active = false; }

    public UUID getId() { return id; }
    public Integer getVersionNumber() { return versionNumber; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getCreatedBy() { return createdBy; }
    public String getDescription() { return description; }
    public boolean isActive() { return active; }
    public List<FormVersionField> getFields() { return fields; }
}