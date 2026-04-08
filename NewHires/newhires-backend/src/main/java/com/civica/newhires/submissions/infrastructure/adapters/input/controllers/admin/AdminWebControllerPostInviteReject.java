package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
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
    private final NotificationPort notificationPort;

    @PostMapping("/submissions/{id}/reject")
    public ResponseEntity<Submission> rejectSubmission(
            @PathVariable UUID id,
            @RequestBody RejectRequest request) {

        // 1. Buscamos la submission (ya incluye datos del candidato y token)
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        // 2. Actualizamos a estado REJECTED
        submission.setStatus(SubmissionStatus.REJECTED);
        
        // 3. Generamos un nuevo token para el re-intento (Reset de token y expiración)
        // Nota: Asegúrate de tener un método resetToken() en tu clase Submission 
        // que genere un nuevo UUID y ponga expiresAt a LocalDateTime.now().plusHours(48)
        String newToken = UUID.randomUUID().toString();
        
        // Si no tienes el método en el dominio, puedes hacerlo vía setters:
        // submission.setToken(newToken);
        // submission.setExpiresAt(LocalDateTime.now().plusHours(48));

        // 4. Persistimos los cambios
        submissionRepository.save(submission);

        // 5. Notificaciones
        try {
            // Notificamos el rechazo
            notificationPort.sendRejectionNotice(submission.getEmail(), request.reason());
            
            // Pausa breve para evitar bloqueos de SMTP si fuera necesario
            Thread.sleep(1000); 

            // Enviamos la nueva invitación con el token actualizado
            notificationPort.sendInvitation(
                submission.getEmail(),
                submission.getCandidateName(),
                newToken
            );
        } catch (Exception e) {
            System.err.println("⚠️ Error en el flujo de notificaciones: " + e.getMessage());
        }

        return ResponseEntity.ok(submission);
    }

    public record RejectRequest(String reason) {}
}