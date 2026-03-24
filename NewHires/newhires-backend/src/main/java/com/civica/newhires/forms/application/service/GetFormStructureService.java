package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.ports.input.GetFormStructureUseCase;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service; 

import java.util.List;

@Service 
@RequiredArgsConstructor
public class GetFormStructureService implements GetFormStructureUseCase {

    private final FormPort formRepository;

    @Override
    public List<FieldDefinition> execute() {
        return formRepository.findAllFieldDefinitions();
    }
}