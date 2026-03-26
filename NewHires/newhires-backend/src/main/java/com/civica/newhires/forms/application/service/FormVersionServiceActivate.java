package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCaseActivate;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormVersionServiceActivate implements ManageFormVersionsUseCaseActivate {

    private final FormVersionPort formVersionRepository;
    private final FormPort formRepository;


@Override
@Transactional
public FormVersion activateVersion(UUID versionId) {
    FormVersion version = formVersionRepository.findById(versionId)
            .orElseThrow(() -> new RuntimeException("Versión no encontrada: " + versionId));

    List<UUID> versionFieldIds = version.getFields().stream()
            .map(vf -> vf.getField().getId())
            .collect(Collectors.toList());

    List<FieldDefinition> currentFields = formRepository.findAllFieldDefinitions();
    currentFields.stream()
            .filter(f -> !versionFieldIds.contains(f.getId()))
            .forEach(f -> formRepository.deleteDefinition(f.getId()));

    version.getFields().forEach(versionField -> {
        FieldDefinition updated = new FieldDefinition(
            versionField.getField().getId(),
            versionField.getField().getLabel(),
            versionField.getField().getType(),
            versionField.getField().isRequired(),
            versionField.getField().getPlaceholder(),
            versionField.getField().getOptions(),
            versionField.getSortOrder()
        );
        formRepository.saveDefinition(updated);
    });

    formVersionRepository.deactivateAll();
    version.activate();
    return formVersionRepository.save(version);
    }

}