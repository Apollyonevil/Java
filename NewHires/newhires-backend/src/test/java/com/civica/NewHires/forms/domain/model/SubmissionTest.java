package com.civica.newhires.forms.domain.model;

import org.junit.jupiter.api.Test;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.UUID;

class SubmissionTest {

    @Test
    void deberiaCrearseConEstadoPendingInvite() {
        Submission submission = new Submission(
            UUID.randomUUID(), "Juan García", "juan@test.com", "token123"
        );
        assertEquals(SubmissionStatus.PENDING_INVITE, submission.getStatus());
    }

    @Test
    void deberiaCaducarCuandoExpiresAtEsAnteriorAHoy() {
        Submission submission = new Submission(
            UUID.randomUUID(), "Juan García", "juan@test.com", "token123"
        );
        submission.setExpiresAt(LocalDateTime.now().minusHours(1));
        assertTrue(submission.isTokenExpired());
    }

    @Test
    void noDeberiaCaducarCuandoExpiresAtEsPosteriorAHoy() {
        Submission submission = new Submission(
            UUID.randomUUID(), "Juan García", "juan@test.com", "token123"
        );
        assertFalse(submission.isTokenExpired());
    }

    @Test
    void deberiaCambiarEstado() {
        Submission submission = new Submission(
            UUID.randomUUID(), "Juan García", "juan@test.com", "token123"
        );
        submission.setStatus(SubmissionStatus.SUBMITTED);
        assertEquals(SubmissionStatus.SUBMITTED, submission.getStatus());
    }
}