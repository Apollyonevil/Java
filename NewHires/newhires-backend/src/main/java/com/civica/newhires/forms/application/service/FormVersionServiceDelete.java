package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCaseDelete;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormVersionServiceDelete implements ManageFormVersionsUseCaseDelete {

    private final FormVersionPort formVersionRepository;

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