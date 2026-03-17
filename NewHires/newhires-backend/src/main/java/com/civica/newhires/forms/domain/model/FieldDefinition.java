package com.civica.newhires.forms.domain.model;

import java.util.List;
import java.util.UUID;

public class FieldDefinition {
    private final UUID id;
    private final String label;          // Ej: "Talla de camiseta"
    private final FieldType type;        // Ej: SELECT
    private final boolean required;      // Ej: true
    private final String placeholder;    // Ej: "Selecciona tu talla"
    private final List<String> options;  // Ej: ["XS", "S", "M", "L", "XL"] (solo para SELECT)

    public FieldDefinition(UUID id, String label, FieldType type, boolean required, String placeholder, List<String> options) {
        this.id = id != null ? id : UUID.randomUUID();
        this.label = label;
        this.type = type;
        this.required = required;
        this.placeholder = placeholder;
        this.options = options;
    }

    // Lógica de dominio: ¿Es un campo de subida de archivos?
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