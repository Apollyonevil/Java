package com.civica.newhires.candidates.infrastructure.config;

import com.civica.newhires.candidates.application.service.InviteCandidateService;
import com.civica.newhires.candidates.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CandidateBeanConfig {

    @Bean
    public InviteCandidateUseCase inviteCandidateUseCase(
            SubmissionRepository submissionRepository,
            NotificationPort notificationPort) {
        return new InviteCandidateService(
            submissionRepository,
            notificationPort
        );
    }
}