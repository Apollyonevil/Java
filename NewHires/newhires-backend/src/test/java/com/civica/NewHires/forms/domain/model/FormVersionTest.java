package com.civica.newhires.forms.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

class FormVersionTest {

    @Test
    void deberiaActivarse() {
        FormVersion version = new FormVersion(
            UUID.randomUUID(), 1, null, "admin", "v1", false, null
        );
        version.activate();
        assertTrue(version.isActive());
    }

    @Test
    void deberiaDesactivarse() {
        FormVersion version = new FormVersion(
            UUID.randomUUID(), 1, null, "admin", "v1", true, null
        );
        version.deactivate();
        assertFalse(version.isActive());
    }
}