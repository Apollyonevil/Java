package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/fields")
@RequiredArgsConstructor
public class AdminFieldController {

    private final ManageFieldsUseCase manageFieldsUseCase;

    @PostMapping
    public ResponseEntity<FieldDefinition> create(@RequestBody FieldDefinition field) {
        return ResponseEntity.ok(manageFieldsUseCase.createField(field));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldDefinition> update(@PathVariable UUID id, @RequestBody FieldDefinition field) {
        return ResponseEntity.ok(manageFieldsUseCase.updateField(id, field));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        manageFieldsUseCase.deleteField(id);
        return ResponseEntity.noContent().build();
    }
}