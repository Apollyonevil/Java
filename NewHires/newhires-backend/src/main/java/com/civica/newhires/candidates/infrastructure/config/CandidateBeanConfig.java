package com.civica.newhires.candidates.infrastructure.config;

import com.civica.newhires.candidates.application.service.InviteCandidateService;
import com.civica.newhires.candidates.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionStatusHistoryPort;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CandidateBeanConfig {

    @Bean
    public InviteCandidateUseCase inviteCandidateUseCase(
            SubmissionPort submissionRepository,
            AccessTokenPort accessTokenRepository,
            SubmissionStatusHistoryPort historyRepository,
            ApplicationEventPublisher eventPublisher) {
        
        return new InviteCandidateService(
            submissionRepository,
            accessTokenRepository,
            historyRepository,
            eventPublisher
        );
    }
}