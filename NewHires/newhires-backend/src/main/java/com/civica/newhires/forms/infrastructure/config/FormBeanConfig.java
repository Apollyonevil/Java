package com.civica.newhires.forms.infrastructure.config;

import com.civica.newhires.forms.application.service.*;
import com.civica.newhires.forms.domain.ports.input.*;
import com.civica.newhires.forms.domain.ports.output.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class FormBeanConfig {

    @Bean
    public FileNameGenerator fileNameGenerator() {
        return new FileNameGenerator();
    }

    @Bean
    public FormValidator formValidator() {
        return new FormValidator();
    }

    @Bean
    public GetSubmissionsUseCase getSubmissionsUseCase(FormRepository formRepository) {
        // La interfaz es el tipo de retorno, el Service es la instancia
        return new GetSubmissionsService(formRepository);
    }

    @Bean
    public SubmitFormUseCase submitFormUseCase(
            FormRepository repo, 
            UserIdentityPort identity, // <-- ¡Añade este!
            FileNameGenerator gen, 
            FormValidator val, 
            FileStoragePort storage) {

        return new SubmitFormService(repo, identity, gen, val, storage);
    }

    @Bean
    public ManageFieldsUseCase manageFieldsUseCase(FormRepository formRepository) {
        return new FieldManagementService(formRepository);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Para que no explote con las fechas
        return mapper;
    }
}