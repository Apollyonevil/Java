package com.civica.newhires.fields.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.model.FieldType;
import com.civica.newhires.fields.domain.ports.input.ManageFieldsUseCaseCreate;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerCreateField {

    private final ManageFieldsUseCaseCreate manageFieldsUseCase;

    // Record bien definido: Sin métodos ni variables de instancia dentro
    public record FieldRequest(
        UUID id,
        String label,
        String type,
        boolean required,
        String placeholder,
        String fileNamingPrefix,
        List<String> options,
        Integer sortOrder,
        boolean active
    ) {}

    @PostMapping("/fields")
    public ResponseEntity<FieldDefinition> saveField(@RequestBody FieldRequest request) {
        FieldDefinition field = new FieldDefinition(
            request.id(), // <--- Cambiado: Si el Front envía ID, lo usamos para editar
            request.label(),
            FieldType.valueOf(request.type().toUpperCase()), // .toUpperCase() para evitar errores de case
            request.required(),
            request.placeholder(),
            request.fileNamingPrefix(),
            request.options(),
            request.sortOrder(),
            true
        );
        return ResponseEntity.ok(manageFieldsUseCase.createField(field));
    }
}