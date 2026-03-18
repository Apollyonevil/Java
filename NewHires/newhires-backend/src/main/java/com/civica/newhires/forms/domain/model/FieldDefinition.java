package com.civica.newhires.forms.domain.model;

import java.util.List;
import java.util.UUID;

public class FieldDefinition {
    private final UUID id;
    private final String label;       
    private final FieldType type;       
    private final boolean required;     
    private final String placeholder;  
    private final List<String> options;  

    public FieldDefinition(UUID id, String label, FieldType type, boolean required, String placeholder, List<String> options) {
        this.id = id != null ? id : UUID.randomUUID();
        this.label = label;
        this.type = type;
        this.required = required;
        this.placeholder = placeholder;
        this.options = options;
    }

    public boolean isFileField() {
        return type == FieldType.PDF || type == FieldType.JPG;
    }

    // Getters
    public UUID getId() { return id; }
    public String getLabel() { return label; }
    public FieldType getType() { return type; }
    public boolean isRequired() { return required; }
    public String getPlaceholder() { return placeholder; }
    public List<String> getOptions() { return options; }
}