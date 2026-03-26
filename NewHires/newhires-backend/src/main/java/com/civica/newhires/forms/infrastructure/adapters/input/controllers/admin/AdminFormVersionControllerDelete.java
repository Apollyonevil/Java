package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCaseDelete;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/versions")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminFormVersionControllerDelete {

    private final ManageFormVersionsUseCaseDelete manageFormVersionsUseCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVersion(@PathVariable UUID id) {
        manageFormVersionsUseCase.deleteVersion(id);
        return ResponseEntity.noContent().build();
    }
}