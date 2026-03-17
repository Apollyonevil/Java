package com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities;

import com.civica.newhires.forms.domain.model.FieldType;
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

    @ElementCollection // Para guardar la lista de opciones (SELECT)
    @CollectionTable(name = "field_options", joinColumns = @JoinColumn(name = "field_id"))
    private List<String> options;
}