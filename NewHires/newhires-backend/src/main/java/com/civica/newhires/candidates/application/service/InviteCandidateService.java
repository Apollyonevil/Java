package com.civica.newhires.candidates.application.service;

import com.civica.newhires.submissions.domain.events.SubmissionInvitationEvent;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.model.SubmissionStatusHistory;
import com.civica.newhires.candidates.domain.model.AccessToken;
import com.civica.newhires.candidates.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionStatusHistoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteCandidateService implements InviteCandidateUseCase {
    
    private final SubmissionPort submissionRepository;
    private final AccessTokenPort accessTokenRepository;
    private final SubmissionStatusHistoryPort historyRepository;
    private final ApplicationEventPublisher eventPublisher; 

    @Override
    @Transactional
    public Submission execute(String name, String email, UUID employeeId) {

        Submission submission = new Submission(employeeId, name, email);
        Submission saved = submissionRepository.save(submission);

        AccessToken token = new AccessToken(saved.getId());
        accessTokenRepository.save(token);

        historyRepository.save(new SubmissionStatusHistory(
            saved.getId(),
            SubmissionStatus.PENDING_INVITE,
            employeeId.toString()
        ));

        saved.setToken(token.getToken());
        saved.setExpiresAt(token.getExpiresAt());

        eventPublisher.publishEvent(new SubmissionInvitationEvent(
                saved.getId(), 
                token.getToken()
            ));

        return saved;
    }
}