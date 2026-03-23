package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import com.civica.newhires.forms.domain.ports.output.FormVersionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class FieldManagementService implements ManageFieldsUseCase {

    private final FormRepository formRepository;
    private final FormVersionRepository formVersionRepository;

    @Override
    public FieldDefinition createField(FieldDefinition field) {
        formVersionRepository.deactivateAll();
        return formRepository.saveDefinition(field);
    }

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

    @Override
    public void deleteField(UUID id) {
        formVersionRepository.deactivateAll();
        formRepository.deleteDefinition(id);
    }
}