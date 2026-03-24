package com.civica.newhires.submissions.infrastructure.config;

import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.submissions.application.service.GetSubmissionsService;
import com.civica.newhires.submissions.application.service.InviteCandidateService;
import com.civica.newhires.submissions.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.submissions.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormBeanConfig {

    @Bean
    public GetSubmissionsUseCase getSubmissionsUseCase(SubmissionRepository submissionRepository) {
        return new GetSubmissionsService(submissionRepository);
    }

    @Bean
    public InviteCandidateUseCase inviteCandidateUseCase(
            SubmissionRepository submissionRepository,
            UserIdentityPort userIdentityPort,
            NotificationPort notificationPort) {
        return new InviteCandidateService(submissionRepository, userIdentityPort, notificationPort);
    }

    
}