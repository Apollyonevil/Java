package com.civica.newhires.employee.infrastructure.adapters.output.identity;

import com.civica.newhires.candidates.domain.model.AccessToken;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.employee.domain.ports.output.UserIdentityPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserIdentityAdapter implements UserIdentityPort {

    private final SubmissionPort submissionRepository;
    private final AccessTokenPort accessTokenRepository; 

    @Override
    public Optional<UUID> findEmployeeIdByToken(String token) {
    
        return accessTokenRepository.findByToken(token)
                .filter(AccessToken::isValid)
                .flatMap(at -> submissionRepository.findById(at.getSubmissionId()))
                .map(s -> s.getEmployeeId());
    }

    @Override
    public Optional<String> findEmailByToken(String token) {
        return accessTokenRepository.findByToken(token)
                .filter(AccessToken::isValid)
                .flatMap(at -> submissionRepository.findById(at.getSubmissionId()))
                .map(s -> s.getEmail());
    }

    @Override
    public Optional<String> findNameByToken(String token) {
        return accessTokenRepository.findByToken(token)
                .filter(AccessToken::isValid)
                .flatMap(at -> submissionRepository.findById(at.getSubmissionId()))
                .map(s -> s.getCandidateName());
    }

    @Override
    public void registerPendingInvite(String name, String email, String token) {
       
    }
}