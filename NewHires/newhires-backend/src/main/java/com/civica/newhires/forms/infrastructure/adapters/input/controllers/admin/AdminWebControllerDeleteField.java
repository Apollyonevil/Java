package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCaseDelete;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerDeleteField {

    private final ManageFieldsUseCaseDelete manageFieldsUseCase;



    @DeleteMapping("/fields/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable UUID id) {
        manageFieldsUseCase.deleteField(id);
        return ResponseEntity.noContent().build();
    }

}