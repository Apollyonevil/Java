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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminFormVersionControllerGM.class)
class AdminFormVersionControllerGMTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ManageFormVersionsUseCase manageFormVersionsUseCase;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaObtenerTodasLasVersiones() throws Exception {
        FormVersion version = new FormVersion(UUID.randomUUID(), 1, null, "admin", "v1", true, List.of());
        when(manageFormVersionsUseCase.getAllVersions()).thenReturn(List.of(version));

        mockMvc.perform(get("/api/admin/versions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].versionNumber").value(1));
    }
}