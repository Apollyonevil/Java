package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminFormVersionControllerPM.class)
class AdminFormVersionControllerPMTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ManageFormVersionsUseCase manageFormVersionsUseCase;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaCrearVersionCorrectamente() throws Exception {
        FormVersion version = new FormVersion(UUID.randomUUID(), 1, null, "admin", "Primera versión", true, List.of());
        when(manageFormVersionsUseCase.createVersion(any(), any())).thenReturn(version);

        mockMvc.perform(post("/api/admin/versions")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"createdBy\":\"admin\",\"description\":\"Primera versión\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.versionNumber").value(1));
    }
}