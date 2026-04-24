package com.civica.newhires.forms.domain.model;

import org.junit.jupiter.api.Test;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.UUID;

class SubmissionTest {

    private Submission crearSubmission() {
        return new Submission(UUID.randomUUID(), UUID.randomUUID(), "token123");
    }

    @Test
    void deberiaCrearseConEstadoPendingInvite() {
        Submission submission = crearSubmission();
        assertEquals(SubmissionStatus.PENDING_INVITE, submission.getStatus());
    }

    @Test
    void deberiaCaducarCuandoExpiresAtEsAnteriorAHoy() {
        Submission submission = crearSubmission();
        submission.setExpiresAt(LocalDateTime.now().minusHours(1));
        assertTrue(submission.isTokenExpired());
    }

    @Test
    void noDeberiaCaducarCuandoExpiresAtEsPosteriorAHoy() {
        Submission submission = crearSubmission();
        submission.setExpiresAt(LocalDateTime.now().plusHours(48));
        assertFalse(submission.isTokenExpired());
    }

    @Test
    void deberiaCambiarEstado() {
        Submission submission = crearSubmission();
        submission.setStatus(SubmissionStatus.SUBMITTED);
        assertEquals(SubmissionStatus.SUBMITTED, submission.getStatus());
    }

    @Test
    void deberiaTenerTokenNoNulo() {
        Submission submission = crearSubmission();
        assertNotNull(submission.getToken());
    }

    @Test
    void deberiaActualizarToken() {
        Submission submission = crearSubmission();
        submission.setToken("token-nuevo");
        assertEquals("token-nuevo", submission.getToken());
    }
}