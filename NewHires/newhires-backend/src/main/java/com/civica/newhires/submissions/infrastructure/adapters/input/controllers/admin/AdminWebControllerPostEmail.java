package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
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
public class AdminWebControllerPostEmail {

    private final SubmissionRepository submissionRepository;
    private final CandidateRepository candidateRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final NotificationPort notificationPort;

    @PostMapping("/send-email/{id}")
    public ResponseEntity<Void> sendManualEmail(@PathVariable UUID id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        var candidate = candidateRepository.findById(submission.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado: " + submission.getCandidateId()));

        var accessToken = accessTokenRepository.findValidBySubmissionId(submission.getId())
                .orElseThrow(() -> new RuntimeException("No hay token válido para la submission: " + id));

        notificationPort.sendInvitation(
            candidate.getEmail(),
            candidate.getCandidateName(),
            accessToken.getToken()
        );

        return ResponseEntity.ok().build();
    }
}