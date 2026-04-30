package com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.fields.infrastructure.adapters.output.persistence.mappers.FieldPersistenceMapper;
import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.model.FormVersionField;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionFieldEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormVersionPersistenceMapper {

    private final FieldPersistenceMapper fieldMapper; // ← delega para FieldDefinition

    public FormVersion toDomain(FormVersionEntity entity) {
        if (entity == null) return null;

        List<FormVersionField> fields = entity.getFields() == null ? List.of() :
                entity.getFields().stream()
                        .map(vf -> new FormVersionField(
                                vf.getId(),
                                entity.getId(),
                                fieldMapper.toDomain(vf.getField()),
                                vf.getSortOrder()
                        ))
                        .collect(Collectors.toList());

        return new FormVersion(
                entity.getId(),
                entity.getVersionNumber(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getDescription(),
                entity.isActive(),
                fields
        );
    }
}