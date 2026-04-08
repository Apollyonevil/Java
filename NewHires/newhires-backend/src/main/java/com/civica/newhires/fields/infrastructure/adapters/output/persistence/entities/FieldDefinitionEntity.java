package com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities;

import com.civica.newhires.fields.domain.model.FieldType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "field_definitions")
@Getter @Setter
public class FieldDefinitionEntity {
    @Id
    private UUID id;

    private String label;

    @Enumerated(EnumType.STRING)
    private FieldType type;

    private boolean required;
    private String placeholder;

    @Column(name = "file_naming_prefix")
    private String fileNamingPrefix;

    private boolean active;

    @Column(name = "sort_order") 
    private Integer sortOrder;

    @ElementCollection 
    @CollectionTable(name = "field_options", joinColumns = @JoinColumn(name = "field_id"))
    @Column(name = "options")
    @OrderColumn(name = "option_order")
    private List<String> options;
}