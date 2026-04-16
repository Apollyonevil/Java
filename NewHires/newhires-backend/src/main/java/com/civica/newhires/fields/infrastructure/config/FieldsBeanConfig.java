package com.civica.newhires.fields.infrastructure.config;

import com.civica.newhires.employee.domain.ports.output.UserIdentityPort;
import com.civica.newhires.fields.domain.service.FormDomainService;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import com.civica.newhires.fields.application.service.*;
import com.civica.newhires.fields.domain.ports.input.*;
import com.civica.newhires.fields.domain.ports.output.*;
import com.civica.newhires.submissions.application.service.SubmitFormService;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionStatusHistoryPort;
import com.civica.newhires.documents.domain.ports.input.UploadFileUseCase;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class FieldsBeanConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement(
            "",
            20971520,
            52428800,
            0
        );
    }

    @Bean
    public FormDomainService formDomainService() {
        return new FormDomainService();
    }

@Bean
public SubmitFormUseCase submitFormUseCase(
        FormPort formRepo,
        SubmissionPort submissionRepo,
        AccessTokenPort accessTokenRepository,
        UserIdentityPort identity,
        UploadFileUseCase uploadFileUseCase,
        FormDomainService formDomainService,
        SubmissionStatusHistoryPort historyRepository) {
    
    return new SubmitFormService(
        formRepo,
        submissionRepo,
        accessTokenRepository,
        identity,
        uploadFileUseCase,
        formDomainService,
        historyRepository
    );
}


    @Bean
    public ManageFieldsUseCaseCreate manageFieldsUseCaseCreate(
            FormPort formRepository,
            FormVersionPort formVersionRepository) {
        return new FieldManagementServiceCreate(formRepository, formVersionRepository);
    }

    @Bean
    public ManageFieldsUseCaseUpdate manageFieldsUseCaseUpdate(
            FormPort formRepository,
            FormVersionPort formVersionRepository) {
        return new FieldManagementServiceUpdate(formRepository, formVersionRepository);
    }

    @Bean
    public ManageFieldsUseCaseDelete manageFieldsUseCaseDelete(
            FormPort formRepository,
            FormVersionPort formVersionRepository) {
        return new FieldManagementServiceDelete(formRepository, formVersionRepository);
    }

    @Bean
    public GetFormStructureUseCase getFormStructureUseCase(FormPort formRepository) {
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