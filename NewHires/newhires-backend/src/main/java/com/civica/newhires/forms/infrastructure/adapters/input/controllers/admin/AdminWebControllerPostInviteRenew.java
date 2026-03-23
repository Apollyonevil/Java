package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.forms.domain.ports.output.NotificationPort;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostInviteRenew {

    private final SubmissionRepository submissionRepository;
    private final NotificationPort notificationPort;

    @PostMapping("/submissions/{id}/renew")
    public ResponseEntity<Submission> renewToken(@PathVariable UUID id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        String newToken = UUID.randomUUID().toString();
        submission.setToken(newToken);
        submission.setExpiresAt(LocalDateTime.now().plusHours(48));
        submissionRepository.save(submission);

        notificationPort.sendInvitation(
            submission.getEmail(),
            submission.getCandidateName(),
            newToken
        );

        return ResponseEntity.ok(submission);
    }

    public static class InviteRequest {
        private String candidateName;
        private String email;

        public InviteRequest() {}
        public String getCandidateName() { return candidateName; }
        public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}