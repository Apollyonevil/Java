package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCaseGetActive;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FormVersionServiceGetActive implements ManageFormVersionsUseCaseGetActive {

    private final FormVersionPort formVersionRepository;
    private final FormPort formRepository;

    @Override
    public Optional<FormVersion> getActiveVersion() {
        return formVersionRepository.findActive();
    }

}