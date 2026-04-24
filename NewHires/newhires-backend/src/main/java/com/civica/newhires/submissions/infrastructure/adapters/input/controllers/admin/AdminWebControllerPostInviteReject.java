package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.AccessToken;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.ports.output.AccessTokenRepository;
import com.civica.newhires.submissions.domain.ports.output.CandidateRepository;
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
public class AdminWebControllerPostInviteReject {

    private final SubmissionRepository submissionRepository;
    private final CandidateRepository candidateRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final NotificationPort notificationPort;

    @PostMapping("/submissions/{id}/reject")
    public ResponseEntity<Submission> rejectSubmission(
            @PathVariable UUID id,
            @RequestBody RejectRequest request) {

        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        var candidate = candidateRepository.findById(submission.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado: " + submission.getCandidateId()));

        submission.setStatus(SubmissionStatus.REJECTED);
        submissionRepository.save(submission);

        accessTokenRepository.invalidateAllBySubmissionId(submission.getId());
        AccessToken newToken = new AccessToken(submission.getId());
        accessTokenRepository.save(newToken);

        try {
            notificationPort.sendRejectionNotice(candidate.getEmail(), request.reason());
            Thread.sleep(1500);
            notificationPort.sendInvitation(
                candidate.getEmail(),
                candidate.getCandidateName(),
                newToken.getToken()
            );
        } catch (Exception e) {
            System.err.println("⚠️ Email no enviado: " + e.getMessage());
        }

        return ResponseEntity.ok(submission);
    }

    public record RejectRequest(String reason) {}
}