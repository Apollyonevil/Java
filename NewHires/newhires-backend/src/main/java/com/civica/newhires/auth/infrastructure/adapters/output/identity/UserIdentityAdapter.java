package com.civica.newhires.auth.infrastructure.adapters.output.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserIdentityAdapter implements UserIdentityPort {

    private final SubmissionRepository submissionRepository;

    @Override
    public Optional<UUID> findEmployeeIdByToken(String token) {
        return submissionRepository.findByToken(token)
                .map(submission -> submission.getEmployeeId());
    }

    @Override
    public Optional<String> findEmailByToken(String token) {
        return submissionRepository.findByToken(token)
                .map(submission -> submission.getEmail());
    }

    @Override
    public Optional<String> findNameByToken(String token) {
        return submissionRepository.findByToken(token)
                .map(submission -> submission.getCandidateName());
    }

    @Override
    public void registerPendingInvite(String name, String email, String token) {
        System.out.println("Invitación registrada para: " + email);
    }
}