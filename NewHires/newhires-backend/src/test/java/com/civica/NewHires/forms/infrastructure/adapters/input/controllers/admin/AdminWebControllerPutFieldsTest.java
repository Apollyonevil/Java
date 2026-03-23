package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldType;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminWebControllerPutFields.class)
class AdminWebControllerPutFieldsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ManageFieldsUseCase manageFieldsUseCase;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaActualizarCampoCorrectamente() throws Exception {
        UUID id = UUID.randomUUID();
        FieldDefinition field = new FieldDefinition(id, "Nombre actualizado", FieldType.TEXT, true, null, null, 0);
        when(manageFieldsUseCase.updateField(eq(id), any())).thenReturn(field);

        mockMvc.perform(put("/api/admin/forms/fields/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"label\":\"Nombre actualizado\",\"type\":\"TEXT\",\"required\":true,\"placeholder\":null,\"options\":null,\"sortOrder\":0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Nombre actualizado"));
    }
}