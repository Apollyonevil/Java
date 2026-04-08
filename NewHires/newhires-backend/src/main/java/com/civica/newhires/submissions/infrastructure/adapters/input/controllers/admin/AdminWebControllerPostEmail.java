package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.model.Submission;
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
    private final NotificationPort notificationPort;

    @PostMapping("/send-email/{id}")
    public ResponseEntity<Void> sendManualEmail(@PathVariable UUID id) {
        // 1. Buscamos la submission por su ID.
        // El objeto devuelto ya contiene el email, el nombre y el token.
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        // 2. Verificamos si el token ha expirado antes de enviarlo
        if (submission.isTokenExpired()) {
            throw new RuntimeException("El token asociado a esta invitación ha expirado.");
        }

        // 3. Enviamos la invitación usando los datos integrados en el dominio
        notificationPort.sendInvitation(
            submission.getEmail(),
            submission.getCandidateName(),
            submission.getToken()
        );

        return ResponseEntity.ok().build();
    }
}