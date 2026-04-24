package com.civica.newhires.candidates.application.service;

import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.candidates.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteCandidateService implements InviteCandidateUseCase {
    
    private final SubmissionRepository submissionRepository;
    private final NotificationPort notificationPort;

    @Override
    @Transactional
    public Submission execute(String name, String email, UUID employeeId) {
        String token = UUID.randomUUID().toString();
        
        // Creamos el objeto de dominio. El constructor se encarga del resto.
        Submission submission = new Submission(employeeId, name, email, token);
        
        // El adaptador gestionará la cascada de guardado
        Submission savedSubmission = submissionRepository.save(submission);

        notificationPort.sendInvitation(email, name, token);
        
        return savedSubmission;
    }
}