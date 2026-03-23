package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/versions")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminFormVersionControllerPM {

    private final ManageFormVersionsUseCase manageFormVersionsUseCase;


    @PostMapping
    public ResponseEntity<FormVersion> createVersion(@RequestBody VersionRequest request) {
        return ResponseEntity.ok(manageFormVersionsUseCase.createVersion(
                request.createdBy(),
                request.description()
        ));
    }

    public record VersionRequest(String createdBy, String description) {}

    
}