package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCaseUpdate;
import com.civica.newhires.forms.domain.ports.output.FormPort;
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
            field.getOptions(),
            field.getSortOrder()
        );
        formVersionRepository.deactivateAll();
        FieldDefinition saved = formRepository.saveDefinition(updatedField);

        if (saved.getOptions() != null) {
            saved.getOptions().size();
        }

        return saved;
    }

}