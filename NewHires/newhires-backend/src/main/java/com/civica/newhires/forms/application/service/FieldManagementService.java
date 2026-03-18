package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class FieldManagementService implements ManageFieldsUseCase {

    private final FormRepository formRepository;

    @Override
    public FieldDefinition createField(FieldDefinition field) {
        return formRepository.saveDefinition(field);
    }

    @Override
    public FieldDefinition updateField(UUID id, FieldDefinition field) {
        formRepository.findDefinitionById(id); 
        return formRepository.saveDefinition(field);
    }

    @Override
    public void deleteField(UUID id) {
        formRepository.deleteDefinition(id);
    }
}