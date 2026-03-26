package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.model.FormVersionField;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCaseCreate;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class FormVersionServiceCreate implements ManageFormVersionsUseCaseCreate {

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


        formVersionRepository.deactivateAll();

        FormVersion version = new FormVersion(
                UUID.randomUUID(),
                nextVersionNumber,
                null,
                createdBy,
                description,
                true,
                versionFields
        );

        return formVersionRepository.save(version);
        }


}