package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.input.ManageFormVersionsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminFormVersionControllerPMActivate.class)
class AdminFormVersionControllerPMActivateTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ManageFormVersionsUseCase manageFormVersionsUseCase;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaActivarVersionCorrectamente() throws Exception {
        UUID id = UUID.randomUUID();
        FormVersion version = new FormVersion(id, 1, null, "admin", "v1", true, List.of());
        when(manageFormVersionsUseCase.activateVersion(id)).thenReturn(version);

        mockMvc.perform(post("/api/admin/versions/{id}/activate", id).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(true));
    }
}