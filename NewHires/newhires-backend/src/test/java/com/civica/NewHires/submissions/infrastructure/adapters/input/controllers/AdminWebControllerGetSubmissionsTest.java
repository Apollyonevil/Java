package com.civica.newhires.submissions.infrastructure.adapters.input.controllers;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin.AdminWebControllerGetSubmissions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminWebControllerGetSubmissions.class)
@AutoConfigureMockMvc(addFilters = false)

public class AdminWebControllerGetSubmissionsTest {

    @Autowired 
    private MockMvc mockMvc;

    @MockitoBean 
    private GetSubmissionsUseCase getSubmissionsUseCase;

    @Test
    public void debeRetornarListaDeSubmissions() throws Exception {
    Submission sub = new Submission(UUID.randomUUID(), UUID.randomUUID(), "token");
        
        when(getSubmissionsUseCase.execute()).thenReturn(List.of(sub));

        mockMvc.perform(get("/api/admin/forms/submissions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].candidateName").value("Candidato Test"))
                .andExpect(jsonPath("$[0].email").value("test@test.com"));
    }
}