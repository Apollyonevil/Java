package com.civica.newhires.submissions.application.service;

import com.civica.newhires.submissions.domain.model.AccessToken;
import com.civica.newhires.submissions.domain.model.Candidate;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.model.SubmissionStatusHistory;
import com.civica.newhires.submissions.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.submissions.domain.ports.output.AccessTokenRepository;
import com.civica.newhires.submissions.domain.ports.output.CandidateRepository;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import com.civica.newhires.submissions.domain.ports.output.SubmissionStatusHistoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class InviteCandidateService implements InviteCandidateUseCase {

    private final SubmissionRepository submissionRepository;
    private final CandidateRepository candidateRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final SubmissionStatusHistoryRepository statusHistoryRepository;
    private final NotificationPort notificationPort;

    @Override
    @Transactional
    public Submission execute(String name, String email) {
        UUID employeeId = UUID.randomUUID();

        Candidate candidate = new Candidate(employeeId, name, email);
        candidateRepository.save(candidate);

        Submission submission = new Submission(candidate.getId(), employeeId);
        submissionRepository.save(submission);

        AccessToken accessToken = new AccessToken(submission.getId());
        accessTokenRepository.save(accessToken);

        statusHistoryRepository.save(
            new SubmissionStatusHistory(submission.getId(), SubmissionStatus.PENDING_INVITE, "system")
        );

        notificationPort.sendInvitation(email, name, accessToken.getToken());

        return submission;
    }
}