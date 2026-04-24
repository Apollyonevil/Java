package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.candidates.domain.model.AccessToken;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.model.SubmissionStatusHistory;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionStatusHistoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Slf4j
@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostInviteReject {

    private final SubmissionPort submissionRepository;
    private final AccessTokenPort accessTokenRepository;
    private final NotificationPort notificationPort;
    private final SubmissionStatusHistoryPort historyRepository;
    
    @PersistenceContext
    private EntityManager entityManager;  // ← añade esto

    @Transactional
    @PostMapping("/submissions/{id}/reject")
    public ResponseEntity<Submission> rejectSubmission(
            @PathVariable UUID id,
            @RequestBody RejectRequest request) {

        log.info("[REJECT] Iniciando rechazo para submission: {}", id);

        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + id));

        // Borrar tokens anteriores y forzar flush inmediato
        accessTokenRepository.deleteBySubmissionId(id);
        entityManager.flush();  

        submission.setStatus(SubmissionStatus.REJECTED);
        submissionRepository.save(submission);

        AccessToken accessToken = new AccessToken(id);
        accessTokenRepository.save(accessToken);

        historyRepository.save(new SubmissionStatusHistory(
        submission.getId(),
        SubmissionStatus.REJECTED,
        "admin"
));

        log.info("[REJECT] Nuevo token generado y persistido: {}", accessToken.getToken());

        try {
            notificationPort.sendRejectionNotice(submission.getEmail(), request.reason());
            notificationPort.sendInvitation(
                submission.getEmail(),
                submission.getCandidateName(),
                accessToken.getToken()
            );
        } catch (Exception e) {
            log.error("[REJECT] Error en notificaciones: {}", e.getMessage());
        }

        return ResponseEntity.ok(submission);
    }

    public record RejectRequest(String reason) {}
}