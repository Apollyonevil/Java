package com.civica.newhires.notification.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.candidates.domain.model.AccessToken;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostEmail {

    private final SubmissionPort submissionRepository;
    private final AccessTokenPort accessTokenRepository; // Inyectamos el repositorio de tokens
    private final NotificationPort notificationPort;

    @PostMapping("/send-email/{id}")
    public ResponseEntity<Void> sendManualEmail(@PathVariable UUID id) {

        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));


        AccessToken activeToken = accessTokenRepository.findValidBySubmissionId(id)
                .orElseThrow(() -> new RuntimeException("No existe un token activo o válido para esta invitación. Por favor, use 'Renew' para generar uno nuevo."));


        notificationPort.sendInvitation(
            submission.getEmail(),
            submission.getCandidateName(),
            activeToken.getToken()
        );

        return ResponseEntity.ok().build();
    }
}