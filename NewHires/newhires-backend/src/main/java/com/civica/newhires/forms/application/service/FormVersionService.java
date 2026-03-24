package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.model.FormVersionField;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCase;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class FormVersionService implements ManageFormVersionsUseCase {

    private final FormVersionPort formVersionRepository;
    private final FormPort formRepository;

    @Override
    @Transactional
    public FormVersion createVersion(String createdBy, String description) {
    List<FieldDefinition> currentFields = formRepository.findAllFieldDefinitions();

    List<FormVersion> existing = formVersionRepository.findAll();
    int nextVersionNumber = existing.stream()
            .mapToInt(FormVersion::getVersionNumber)
            .max()
            .orElse(0) + 1;

    List<FormVersionField> versionFields = IntStream.range(0, currentFields.size())
            .mapToObj(i -> new FormVersionField(
                    UUID.randomUUID(),
                    null,
                    currentFields.get(i),
                    i
            ))
            .collect(Collectors.toList());

    // Desactivamos todas las versiones anteriores
    formVersionRepository.deactivateAll();

    FormVersion version = new FormVersion(
            UUID.randomUUID(),
            nextVersionNumber,
            null,
            createdBy,
            description,
            true, // <-- se activa directamente
            versionFields
    );

    return formVersionRepository.save(version);
}

@Override
@Transactional
public FormVersion activateVersion(UUID versionId) {
    FormVersion version = formVersionRepository.findById(versionId)
            .orElseThrow(() -> new RuntimeException("Versión no encontrada: " + versionId));

    // IDs de campos que pertenecen a esta versión
    List<UUID> versionFieldIds = version.getFields().stream()
            .map(vf -> vf.getField().getId())
            .collect(Collectors.toList());

    // Eliminamos campos que NO están en esta versión
    List<FieldDefinition> currentFields = formRepository.findAllFieldDefinitions();
    currentFields.stream()
            .filter(f -> !versionFieldIds.contains(f.getId()))
            .forEach(f -> formRepository.deleteDefinition(f.getId()));

    // Restauramos el orden de los campos de la versión
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

    @Override
    public List<FormVersion> getAllVersions() {
        return formVersionRepository.findAll();
    }

    @Override
    public Optional<FormVersion> getActiveVersion() {
        return formVersionRepository.findActive();
    }

    @Override
    @Transactional
    public void deleteVersion(UUID versionId) {
        FormVersion version = formVersionRepository.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Versión no encontrada: " + versionId));

        if (version.isActive()) {
            throw new RuntimeException("No se puede eliminar la versión activa");
        }

        formVersionRepository.deleteById(versionId);
    }
}