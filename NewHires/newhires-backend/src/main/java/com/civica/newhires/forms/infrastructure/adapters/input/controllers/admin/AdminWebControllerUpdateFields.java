package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldType;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCaseUpdate;
import com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin.AdminWebControllerCreateField.FieldRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerUpdateFields {

    private final ManageFieldsUseCaseUpdate manageFieldsUseCase;


    @PutMapping("/fields/{id}")
    public ResponseEntity<FieldDefinition> updateField(@PathVariable UUID id, @RequestBody FieldRequest request) {
        FieldDefinition field = new FieldDefinition(
            id,
            request.label(),
            FieldType.valueOf(request.type()),
            request.required(),
            request.placeholder(),
            request.options(),
            request.sortOrder()
        );
        return ResponseEntity.ok(manageFieldsUseCase.updateField(id, field));
    }
}