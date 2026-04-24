package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
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
public class AdminWebControllerPostInviteRenew {

    private final SubmissionRepository submissionRepository;
    private final NotificationPort notificationPort;

    @PostMapping("/submissions/{id}/renew")
    public ResponseEntity<Submission> renewToken(@PathVariable UUID id) {
        // 1. Buscamos la submission por su ID
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        // 2. Generamos los nuevos datos del token directamente en la submission
        // Al sobreescribir el token antiguo, el anterior queda automáticamente invalidado
        String newTokenString = UUID.randomUUID().toString();
        
        // Seteamos el nuevo token y extendemos la fecha de expiración (ej. 48 horas más)
        submission.setToken(newTokenString);
        submission.setExpiresAt(LocalDateTime.now().plusHours(48));

        // 3. Persistimos los cambios (el adaptador se encarga de actualizar MariaDB)
        submissionRepository.save(submission);

        // 4. Enviamos la nueva invitación con los datos que ya tenemos en el objeto
        notificationPort.sendInvitation(
            submission.getEmail(),
            submission.getCandidateName(),
            submission.getToken()
        );

        return ResponseEntity.ok(submission);
    }
}