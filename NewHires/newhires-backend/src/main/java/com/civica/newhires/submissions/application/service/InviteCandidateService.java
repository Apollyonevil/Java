package com.civica.newhires.submissions.application.service;

import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class InviteCandidateService implements InviteCandidateUseCase {

    private final SubmissionRepository submissionRepository;
    private final UserIdentityPort userIdentityPort;
    private final NotificationPort notificationPort;

    @Override
    @Transactional
     public Submission execute(String name, String email) {
        String token = UUID.randomUUID().toString();
        UUID employeeId = UUID.randomUUID(); 

        userIdentityPort.registerPendingInvite(name, email, token);

        Submission submission = new Submission(
            employeeId, 
            name, 
            email, 
            token
        );
        

        submission.setStatus(SubmissionStatus.PENDING_INVITE);

        submissionRepository.save(submission);
        notificationPort.sendInvitation(email, name, token);
        
        return submission;
    }
}