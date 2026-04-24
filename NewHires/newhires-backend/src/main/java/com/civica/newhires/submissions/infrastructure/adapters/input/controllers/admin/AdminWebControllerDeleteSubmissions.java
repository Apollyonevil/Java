package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerDeleteSubmissions {


    private final SubmissionPort submissionRepository;

    @DeleteMapping("/submissions/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable UUID id) {
        submissionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}