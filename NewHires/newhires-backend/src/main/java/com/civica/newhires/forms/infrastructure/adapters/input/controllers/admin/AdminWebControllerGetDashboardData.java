package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.GetSubmissionsUseCase;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerGetDashboardData {

    private final GetSubmissionsUseCase getSubmissionsUseCase;


    @GetMapping("/data")
    public ResponseEntity<List<Submission>> getDashboardData() {
        return ResponseEntity.ok(getSubmissionsUseCase.execute());
    }
}