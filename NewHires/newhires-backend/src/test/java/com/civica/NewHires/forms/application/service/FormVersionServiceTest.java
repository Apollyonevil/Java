package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldType;
import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import com.civica.newhires.forms.domain.ports.output.FormVersionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormVersionServiceTest {

    @Mock
    private FormVersionRepository formVersionRepository;

    @Mock
    private FormRepository formRepository;

    @InjectMocks
    private FormVersionService formVersionService;

    @Test
    void deberiaCrearVersionConCamposActuales() {
        List<FieldDefinition> campos = List.of(
            new FieldDefinition(UUID.randomUUID(), "Nombre", FieldType.TEXT, true, null, null, 0),
            new FieldDefinition(UUID.randomUUID(), "Email", FieldType.TEXT, true, null, null, 1)
        );
        when(formRepository.findAllFieldDefinitions()).thenReturn(campos);
        when(formVersionRepository.findAll()).thenReturn(List.of());
        when(formVersionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        FormVersion result = formVersionService.createVersion("admin", "Primera versión");

        assertNotNull(result);
        assertEquals(1, result.getVersionNumber());
        assertEquals("admin", result.getCreatedBy());
        assertEquals(2, result.getFields().size());
        assertTrue(result.isActive());
    }

    @Test
    void deberiaActivarVersionYDesactivarResto() {
        UUID versionId = UUID.randomUUID();
        FormVersion version = new FormVersion(versionId, 1, null, "admin", "v1", false, List.of());
        when(formVersionRepository.findById(versionId)).thenReturn(Optional.of(version));
        when(formRepository.findAllFieldDefinitions()).thenReturn(List.of());
        when(formVersionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        FormVersion result = formVersionService.activateVersion(versionId);

        assertTrue(result.isActive());
        verify(formVersionRepository, times(1)).deactivateAll();
    }

    @Test
    void deberiaLanzarExcepcionAlActivarVersionInexistente() {
        UUID id = UUID.randomUUID();
        when(formVersionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> formVersionService.activateVersion(id));
    }

    @Test
    void noDeberiaEliminarVersionActiva() {
        UUID id = UUID.randomUUID();
        FormVersion version = new FormVersion(id, 1, null, "admin", "v1", true, List.of());
        when(formVersionRepository.findById(id)).thenReturn(Optional.of(version));

        assertThrows(RuntimeException.class, () -> formVersionService.deleteVersion(id));
    }
}