package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostApprove {

    private final SubmissionRepository submissionRepository;
    private final NotificationPort notificationPort;

    @PostMapping("/submissions/{id}/approve")
    public ResponseEntity<Submission> approveSubmission(@PathVariable UUID id) {
        // Buscamos por el ID de la submission, que es lo que envía Angular
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la submission con ID: " + id));

        submission.setStatus(SubmissionStatus.APPROVED);
        submissionRepository.save(submission);

        notificationPort.sendApprovalNotice(
        submission.getEmail(), 
        submission.getCandidateName()
        );

        return ResponseEntity.ok(submission);
    }
}