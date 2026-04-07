package com.civica.newhires.auth.infrastructure.adapters.output.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.submissions.domain.ports.output.AccessTokenRepository;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import com.civica.newhires.submissions.domain.ports.output.CandidateRepository;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserIdentityAdapter implements UserIdentityPort {

    private final AccessTokenRepository accessTokenRepository;
    private final SubmissionRepository submissionRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public Optional<UUID> findEmployeeIdByToken(String token) {
        return accessTokenRepository.findByToken(token)
                .filter(t -> t.isValid())
                .flatMap(t -> submissionRepository.findById(t.getSubmissionId()))
                .map(submission -> submission.getEmployeeId());
    }

    @Override
    public Optional<String> findEmailByToken(String token) {
        return accessTokenRepository.findByToken(token)
                .filter(t -> t.isValid())
                .flatMap(t -> submissionRepository.findById(t.getSubmissionId()))
                .flatMap(s -> candidateRepository.findById(s.getCandidateId()))
                .map(candidate -> candidate.getEmail());
    }

    @Override
    public Optional<String> findNameByToken(String token) {
        return accessTokenRepository.findByToken(token)
                .filter(t -> t.isValid())
                .flatMap(t -> submissionRepository.findById(t.getSubmissionId()))
                .flatMap(s -> candidateRepository.findById(s.getCandidateId()))
                .map(candidate -> candidate.getCandidateName());
    }

    @Override
    public void registerPendingInvite(String name, String email, String token) {
        System.out.println("Invitación registrada para: " + email);
    }
}