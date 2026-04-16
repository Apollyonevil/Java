package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.RejectSubmissionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostInviteReject {

    private final RejectSubmissionUseCase rejectSubmissionUseCase;

    @PostMapping("/submissions/{id}/reject")
    public ResponseEntity<Submission> rejectSubmission(
            @PathVariable UUID id,
            @RequestBody RejectRequest request) {
        return ResponseEntity.ok(rejectSubmissionUseCase.execute(id, request.reason(), "admin"));
    }

    public record RejectRequest(String reason) {}
}