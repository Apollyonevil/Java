package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCaseDelete;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;

import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class FieldManagementServiceDelete implements ManageFieldsUseCaseDelete {

    private final FormPort formRepository;
    private final FormVersionPort formVersionRepository;

    @Override
    public void deleteField(UUID id) {
        formVersionRepository.deactivateAll();
        formRepository.deleteDefinition(id);
    }
}