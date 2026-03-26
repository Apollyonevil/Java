package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCaseGetActive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/versions")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminFormVersionControllerGetActive {

    private final ManageFormVersionsUseCaseGetActive manageFormVersionsUseCase;


    @GetMapping("/active")
    public ResponseEntity<FormVersion> getActive() {
        return manageFormVersionsUseCase.getActiveVersion()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public record VersionRequest(String createdBy, String description) {}
}