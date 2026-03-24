package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.GetSubmissionsUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminWebControllerGetDashboardData.class)
class AdminWebControllerGetDataTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetSubmissionsUseCase getSubmissionsUseCase;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaObtenerDashboardData() throws Exception {
        Submission submission = new Submission(UUID.randomUUID(), "Juan", "juan@test.com", "token");
        when(getSubmissionsUseCase.execute()).thenReturn(List.of(submission));

        mockMvc.perform(get("/api/admin/forms/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].candidateName").value("Juan"));
    }
}