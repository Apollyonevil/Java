package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/versions")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminFormVersionControllerGetAll {

    private final ManageFormVersionsUseCase manageFormVersionsUseCase;

    @GetMapping
    public ResponseEntity<List<FormVersion>> getAll() {
        return ResponseEntity.ok(manageFormVersionsUseCase.getAllVersions());
    }

    public record VersionRequest(String createdBy, String description) {}
}