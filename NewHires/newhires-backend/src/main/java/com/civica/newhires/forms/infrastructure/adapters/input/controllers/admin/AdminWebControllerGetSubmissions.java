package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.GetSubmissionsUseCase;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerGetSubmissions {


    private final GetSubmissionsUseCase getSubmissionsUseCase;


    @GetMapping("/submissions")
    public ResponseEntity<List<Submission>> getAll() {
        return ResponseEntity.ok(getSubmissionsUseCase.execute());
    }


}