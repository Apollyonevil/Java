package com.civica.newhires.fields.application.service;

import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.ports.input.ManageFieldsUseCaseCreate;
import com.civica.newhires.fields.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FieldManagementServiceCreate implements ManageFieldsUseCaseCreate {

    private final FormPort formRepository;
    private final FormVersionPort formVersionRepository;

    @Override
    public FieldDefinition createField(FieldDefinition field) {
        formVersionRepository.deactivateAll();
        return formRepository.saveDefinition(field);
    }

}