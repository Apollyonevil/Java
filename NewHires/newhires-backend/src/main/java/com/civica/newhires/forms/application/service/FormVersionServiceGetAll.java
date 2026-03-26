package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCaseGetAll;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FormVersionServiceGetAll implements ManageFormVersionsUseCaseGetAll {

    private final FormVersionPort formVersionRepository;
    private final FormPort formRepository;


    @Override
    public List<FormVersion> getAllVersions() {
        return formVersionRepository.findAll();
    }

}