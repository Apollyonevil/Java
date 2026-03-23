package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/versions")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminFormVersionControllerPMActivate {

    private final ManageFormVersionsUseCase manageFormVersionsUseCase;

    @PostMapping("/{id}/activate")
    public ResponseEntity<FormVersion> activateVersion(@PathVariable UUID id) {
        return ResponseEntity.ok(manageFormVersionsUseCase.activateVersion(id));
    }

    public record VersionRequest(String createdBy, String description) {}
}