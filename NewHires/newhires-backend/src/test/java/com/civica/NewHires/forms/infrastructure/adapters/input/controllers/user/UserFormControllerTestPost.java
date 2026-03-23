package com.civica.newhires.forms.infrastructure.adapters.input.controllers.user;

import com.civica.newhires.forms.domain.ports.input.SubmitFormUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserFormControllerPost.class)
@AutoConfigureMockMvc(addFilters = false)
class UserFormControllerPostTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubmitFormUseCase submitFormUseCase;

    @Test
    void deberiaEnviarFormularioCorrectamente() throws Exception {
        doNothing().when(submitFormUseCase).execute(any(), any(), any());

        MockMultipartFile token = new MockMultipartFile(
            "token", "", "text/plain", "mi-token-123".getBytes()
        );

        MockMultipartFile responses = new MockMultipartFile(
            "responses", "", "application/json",
            "[{\"fieldDefinitionId\":\"bafb0b5c-212e-11f1-8314-a6ac6c94ec55\",\"value\":\"Juan\"}]".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/forms/submit")
                .file(token)
                .file(responses))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaRechazarPeticionSinToken() throws Exception {
        mockMvc.perform(multipart("/api/v1/forms/submit"))
                .andExpect(status().isBadRequest());
    }
}