package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.ApproveSubmissionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostApprove {

    private final ApproveSubmissionUseCase approveSubmissionUseCase;

    @PostMapping("/submissions/{id}/approve")
    public ResponseEntity<Submission> approveSubmission(@PathVariable UUID id) {
        return ResponseEntity.ok(approveSubmissionUseCase.execute(id, "admin"));
    }
}