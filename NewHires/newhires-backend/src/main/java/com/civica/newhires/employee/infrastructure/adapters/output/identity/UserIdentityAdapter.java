package com.civica.newhires.employee.infrastructure.adapters.output.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.civica.newhires.employee.domain.ports.output.UserIdentityPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserIdentityAdapter implements UserIdentityPort {

    private final SubmissionRepository submissionRepository;

    @Override
    public Optional<UUID> findEmployeeIdByToken(String token) {
        // BUSCAMOS EN SUBMISSIONS, NO EN ACCESS_TOKENS
        return submissionRepository.findByToken(token)
                .filter(s -> !s.isTokenExpired())
                .map(s -> s.getEmployeeId());
    }

    @Override
    public Optional<String> findEmailByToken(String token) {
        return submissionRepository.findByToken(token)
                .filter(s -> !s.isTokenExpired())
                .map(s -> s.getEmail());
    }

    @Override
    public Optional<String> findNameByToken(String token) {
        return submissionRepository.findByToken(token)
                .filter(s -> !s.isTokenExpired())
                .map(s -> s.getCandidateName());
    }

    @Override
    public void registerPendingInvite(String name, String email, String token) {
       
    }
}