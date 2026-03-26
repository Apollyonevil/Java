package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldType;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldManagementServiceTest {

    @Mock
    private FormPort formRepository;

    @Mock
    private FormVersionPort formVersionRepository;

    @InjectMocks
    private FieldManagementServiceCreate fieldManagementServiceCreate;

    @InjectMocks
    private FieldManagementServiceUpdate fieldManagementServiceUpdate;

    @InjectMocks
    private FieldManagementServiceDelete fieldManagementServiceDelete;

    private FieldDefinition campoEjemplo() {
        return new FieldDefinition(
            UUID.randomUUID(), "Nombre", FieldType.TEXT,
            true, "Escribe tu nombre", null, 0
        );
    }

    @Test
    void deberiaCrearCampo() {
        FieldDefinition campo = campoEjemplo();
        when(formRepository.saveDefinition(any())).thenReturn(campo);

        FieldDefinition result = fieldManagementServiceCreate.createField(campo);

        assertNotNull(result);
        assertEquals("Nombre", result.getLabel());
        verify(formRepository, times(1)).saveDefinition(any());
    }

    @Test
    void deberiaDesactivarVersionesAlCrearCampo() {
        FieldDefinition campo = campoEjemplo();
        when(formRepository.saveDefinition(any())).thenReturn(campo);

        fieldManagementServiceCreate.createField(campo);

        verify(formVersionRepository, times(1)).deactivateAll();
    }

    @Test
    void deberiaEliminarCampo() {
        UUID id = UUID.randomUUID();
        fieldManagementServiceDelete.deleteField(id);
        verify(formRepository, times(1)).deleteDefinition(id);
    }

    @Test
    void deberiaDesactivarVersionesAlEliminarCampo() {
        UUID id = UUID.randomUUID();
        fieldManagementServiceDelete.deleteField(id);
        verify(formVersionRepository, times(1)).deactivateAll();
    }
}