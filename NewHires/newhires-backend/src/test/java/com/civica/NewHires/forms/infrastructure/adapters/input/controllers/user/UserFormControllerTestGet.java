package com.civica.newhires.forms.infrastructure.adapters.input.controllers.user;

import com.civica.newhires.forms.application.dto.FieldDefinitionDTO;
import com.civica.newhires.forms.domain.ports.input.GetFormStructureUseCase;
import com.civica.newhires.forms.infrastructure.adapters.input.web.mappers.FieldDTOMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserFormControllerGet.class)
@AutoConfigureMockMvc(addFilters = false)
class UserFormControllerTestGet {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetFormStructureUseCase getFormStructureUseCase;

    @MockitoBean
    private FieldDTOMapper mapper;

    @Test
    void deberiaObtenerEstructuraDelFormulario() throws Exception {
        FieldDefinitionDTO dto = new FieldDefinitionDTO(
            UUID.randomUUID(), "Nombre", "TEXT", true, null, null, 0
        );
        when(getFormStructureUseCase.execute()).thenReturn(List.of());
        when(mapper.toDTO(any())).thenReturn(dto);

        mockMvc.perform(get("/api/v1/forms/structure"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}