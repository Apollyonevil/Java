package com.civica.newhires.auth.infrastructure.config;

import com.civica.newhires.auth.application.service.SendInvitationService;
import com.civica.newhires.auth.application.service.ValidateAccessService;
import com.civica.newhires.auth.domain.ports.output.InvitationRepository;
import com.civica.newhires.auth.domain.ports.output.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthBeanConfig {

    @Bean
    public SendInvitationService sendInvitationService(
            InvitationRepository repository, 
            NotificationService notificationService) {
        return new SendInvitationService(repository, notificationService);
    }

    @Bean
    public ValidateAccessService validateAccessService(
            InvitationRepository repository) {
        return new ValidateAccessService(repository);
    }
}