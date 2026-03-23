package com.civica.newhires.auth.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class InvitationTest {

    @Test
    void deberiaSerValidaCuandoEstaEnEstadoPendingYNoHaCaducado() {
        Invitation invitation = new Invitation("test@civica.com");
        assertTrue(invitation.isValid());
    }

    @Test
    void noDeberiaSerValidaCuandoHaCaducado() {
        Invitation invitation = new Invitation(
            null, "test@civica.com", "token", 
            LocalDateTime.now().minusHours(1), 
            InvitationStatus.PENDING
        );
        assertFalse(invitation.isValid());
    }

    @Test
    void noDeberiaSerValidaCuandoEstaAceptada() {
        Invitation invitation = new Invitation(
            null, "test@civica.com", "token",
            LocalDateTime.now().plusHours(48),
            InvitationStatus.ACCEPTED
        );
        assertFalse(invitation.isValid());
    }

    @Test
    void deberiaAceptarse() {
        Invitation invitation = new Invitation("test@civica.com");
        invitation.accept();
        assertEquals(InvitationStatus.ACCEPTED, invitation.getStatus());
    }
}