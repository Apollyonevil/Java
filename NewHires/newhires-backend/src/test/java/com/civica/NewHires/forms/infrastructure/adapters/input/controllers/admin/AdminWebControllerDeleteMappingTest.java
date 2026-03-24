package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminWebControllerDeleteField.class)
class AdminWebControllerDeleteMappingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ManageFieldsUseCase manageFieldsUseCase;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaEliminarCampoCorrectamente() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(manageFieldsUseCase).deleteField(id);

        mockMvc.perform(delete("/api/admin/forms/fields/{id}", id).with(csrf()))
                .andExpect(status().isNoContent());
    }
}