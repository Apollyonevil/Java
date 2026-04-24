package com.civica.newhires.fields.domain.model;

import java.util.List;
import java.util.UUID;

public class FieldDefinition {
    private final UUID id;
    private final String label;
    private final FieldType type;
    private final boolean required;
    private final String placeholder;
    private final String fileNamingPrefix;
    private final List<String> options;
    private final Integer sortOrder;
    private boolean active;

    public FieldDefinition(UUID id, String label, FieldType type, boolean required,
                           String placeholder, String fileNamingPrefix,
                           List<String> options, Integer sortOrder, boolean active) {
        this.id = id != null ? id : UUID.randomUUID();
        this.label = label;
        this.type = type;
        this.required = required;
        this.placeholder = placeholder;
        this.fileNamingPrefix = fileNamingPrefix;
        this.options = options;
        this.sortOrder = sortOrder;
        this.active = active;
    }

    public boolean isFileField() {
        return type == FieldType.PDF || type == FieldType.JPG;
    }

    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }

    public UUID getId() { return id; }
    public String getLabel() { return label; }
    public FieldType getType() { return type; }
    public boolean isRequired() { return required; }
    public String getPlaceholder() { return placeholder; }
    public String getFileNamingPrefix() { return fileNamingPrefix; }
    public List<String> getOptions() { return options; }
    public Integer getSortOrder() { return sortOrder; }
    public boolean isActive() { return active; }
}