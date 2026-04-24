package com.civica.newhires.submissions.infrastructure.config;

import com.civica.newhires.submissions.application.service.GetSubmissionsService;
import com.civica.newhires.submissions.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubmissionsBeanConfig {

    @Bean
    public GetSubmissionsUseCase getSubmissionsUseCase(SubmissionPort submissionRepository) {
        return new GetSubmissionsService(submissionRepository);
    }

}