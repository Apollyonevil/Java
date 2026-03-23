package com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "form_version_fields")
public class FormVersionFieldEntity {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private FormVersionEntity version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "field_id", nullable = false)
    private FieldDefinitionEntity field;

    @Column(name = "sort_order")
    private Integer sortOrder;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public FormVersionEntity getVersion() { return version; }
    public void setVersion(FormVersionEntity version) { this.version = version; }

    public FieldDefinitionEntity getField() { return field; }
    public void setField(FieldDefinitionEntity field) { this.field = field; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}