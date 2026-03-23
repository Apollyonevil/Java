package com.civica.newhires.forms.domain.model;

import java.util.UUID;

public class FormVersionField {
    private final UUID id;
    private final UUID versionId;
    private final FieldDefinition field;
    private final Integer sortOrder;

    public FormVersionField(UUID id, UUID versionId, FieldDefinition field, Integer sortOrder) {
        this.id = id != null ? id : UUID.randomUUID();
        this.versionId = versionId;
        this.field = field;
        this.sortOrder = sortOrder;
    }

    public UUID getId() { return id; }
    public UUID getVersionId() { return versionId; }
    public FieldDefinition getField() { return field; }
    public Integer getSortOrder() { return sortOrder; }
}