package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.candidates.domain.model.AccessToken;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostInviteRenew {

    private final SubmissionPort submissionRepository;
    private final AccessTokenPort accessTokenRepository;
    private final NotificationPort notificationPort;

    @PostMapping("/submissions/{id}/renew")
    @Transactional // <--- ESTO ES VITAL: Asegura que el borrado y la inserción sean una sola operación
    public ResponseEntity<Submission> renewToken(@PathVariable UUID id) {
        
      
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));


        accessTokenRepository.deleteBySubmissionId(id);


        AccessToken newToken = new AccessToken(id); 
        accessTokenRepository.save(newToken);

        notificationPort.sendInvitation(
            submission.getEmail(),
            submission.getCandidateName(),
            newToken.getToken()
        );

        return ResponseEntity.ok(submission);
    }
}