package com.civica.newhires.forms.infrastructure.config;

import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.forms.application.service.*;
import com.civica.newhires.forms.domain.ports.input.*;
import com.civica.newhires.forms.domain.ports.output.*;
import com.civica.newhires.forms.domain.service.FormDomainService;
import com.civica.newhires.submissions.application.service.SubmitFormService;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class FormBeanConfig {

@Bean
public MultipartConfigElement multipartConfigElement() {
    return new MultipartConfigElement(
        "",
        20971520,  // 20MB en bytes
        52428800,  // 50MB en bytes
        0
    );
}

    @Bean
    public FormDomainService formDomainService() {
        return new FormDomainService();
    }

    @Bean
    public SubmitFormUseCase submitFormUseCase(
            FormRepository formRepo,
            SubmissionRepository submissionRepo,
            UserIdentityPort identity,
            FileStoragePort storage,
            FormDomainService formDomainService,
            NotificationPort notificationPort) {
        return new SubmitFormService(
            formRepo,
            submissionRepo,
            identity,
            storage,
            formDomainService,
            notificationPort
        );
    }


    @Bean
    public ManageFieldsUseCase manageFieldsUseCase(
        FormRepository formRepository,
        FormVersionRepository formVersionRepository) {
    return new FieldManagementService(formRepository, formVersionRepository);
    }

    @Bean
    public GetFormStructureUseCase getFormStructureUseCase(FormRepository formRepository) {
        return new GetFormStructureService(formRepository);
    }

        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper;
        }


    
}