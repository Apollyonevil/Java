package com.civica.newhires.auth.infrastructure.adapters.output.identity;

import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;

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
        // Ya no necesitamos hacer nada aquí porque la Submission
        // se crea en InviteCandidateService
        System.out.println("Invitación registrada para: " + email);
    }
}