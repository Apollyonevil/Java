package com.civica.newhires.fields.infrastructure.adapters.input.controllers.user;

import com.civica.newhires.fields.application.dto.FieldDefinitionDTO;
import com.civica.newhires.fields.domain.ports.input.GetFormStructureUseCase;
import com.civica.newhires.fields.infrastructure.adapters.input.controllers.web.mappers.FieldDTOMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor

public class UserFormControllerGet {

    private final GetFormStructureUseCase getFormStructureUseCase;
    private final FieldDTOMapper mapper;

    @GetMapping("/structure")
    public ResponseEntity<List<FieldDefinitionDTO>> getStructure() {
        List<FieldDefinitionDTO> fields = getFormStructureUseCase.execute()
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(fields);
    }

}