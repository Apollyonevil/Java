package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.model.SubmissionStatusHistory;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionStatusHistoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostApprove {

    private final SubmissionPort submissionRepository;
    private final NotificationPort notificationPort;
    private final SubmissionStatusHistoryPort historyRepository;

    @PostMapping("/submissions/{id}/approve")
    public ResponseEntity<Submission> approveSubmission(@PathVariable UUID id) {
        // 1. Buscamos la submission. Al recuperarla del adapter, ya trae el nombre y email.
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la submission con ID: " + id));

        // 2. Actualizamos el estado
        submission.setStatus(SubmissionStatus.APPROVED);
        
        // 3. Guardamos los cambios
        submissionRepository.save(submission);

        historyRepository.save(new SubmissionStatusHistory(
        submission.getId(),
        SubmissionStatus.APPROVED,
        "admin" // o puedes obtenerlo del SecurityContext
     ));

        // 4. Notificación usando los datos que ya están en el objeto submission
        notificationPort.sendApprovalNotice(
            submission.getEmail(), 
            submission.getCandidateName()
        );

        return ResponseEntity.ok(submission);
    }
}