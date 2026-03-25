package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostInviteReject {

    private final SubmissionRepository submissionRepository;
    private final NotificationPort notificationPort;
@PostMapping("/submissions/{id}/reject")
public ResponseEntity<Submission> rejectSubmission(
        @PathVariable UUID id,
        @RequestBody RejectRequest request) {

    Submission submission = submissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));


    submission.setStatus(SubmissionStatus.REJECTED);

    String newToken = UUID.randomUUID().toString();
    submission.setToken(newToken);
    submission.setExpiresAt(LocalDateTime.now().plusHours(48));
    submissionRepository.save(submission);


    try {
        notificationPort.sendRejectionNotice(submission.getEmail(), request.reason());
        Thread.sleep(1500);
        notificationPort.sendInvitation(
            submission.getEmail(),
            submission.getCandidateName(),
            newToken
        );
    } catch (Exception e) {
        System.err.println("⚠️ Email no enviado: " + e.getMessage());
    }

    return ResponseEntity.ok(submission);
}

public record RejectRequest(String reason) {}

}