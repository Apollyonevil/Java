package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
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
    private final NotificationPort notificationPort;



    @PostMapping("/send-email/{employeeId}")
    public ResponseEntity<Void> sendManualEmail(@PathVariable UUID employeeId) {
        Submission submission = submissionRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + employeeId));
        
        notificationPort.sendInvitation(
            submission.getEmail(),
            submission.getCandidateName(),
            submission.getToken()
        );
        
        return ResponseEntity.ok().build();
    }
}