package com.civica.newhires.fields.application.service;

import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.ports.input.ManageFieldsUseCaseUpdate;
import com.civica.newhires.fields.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class FieldManagementServiceUpdate implements ManageFieldsUseCaseUpdate {

    private final FormPort formRepository;
    private final FormVersionPort formVersionRepository;

    @Override
    @Transactional
    public FieldDefinition updateField(UUID id, FieldDefinition field) {
        formRepository.findDefinitionById(id);

        FieldDefinition updatedField = new FieldDefinition(
            id,
            field.getLabel(),
            field.getType(),
            field.isRequired(),
            field.getPlaceholder(),
            field.getFileNamingPrefix(),
            field.getOptions(),
            field.getSortOrder(),
            field.isActive()
        );
        formVersionRepository.deactivateAll();
        FieldDefinition saved = formRepository.saveDefinition(updatedField);

        if (saved.getOptions() != null) {
            saved.getOptions().size();
        }

        return saved;
    }

    @Override
    @Transactional
    public FieldDefinition toggleField(UUID id) {
        FieldDefinition existing = formRepository.findDefinitionById(id);
        FieldDefinition toggled = new FieldDefinition(
            existing.getId(), existing.getLabel(), existing.getType(),
            existing.isRequired(), existing.getPlaceholder(),
            existing.getFileNamingPrefix(), existing.getOptions(),
            existing.getSortOrder(),
            !existing.isActive()
        );
        formVersionRepository.deactivateAll();
        return formRepository.saveDefinition(toggled);
    }
}