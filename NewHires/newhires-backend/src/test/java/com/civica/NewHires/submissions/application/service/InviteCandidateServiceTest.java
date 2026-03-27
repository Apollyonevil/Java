package com.civica.newhires.submissions.application.service;

import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InviteCandidateServiceTest {

    @Mock private SubmissionRepository submissionRepository;
    @Mock private UserIdentityPort userIdentityPort;
    @Mock private NotificationPort notificationPort;

    @InjectMocks private InviteCandidateService inviteCandidateService;

    @Test
    void debeGenerarInvitacionCorrectamente() {
        // Arrange
        String name = "Pepe Perez";
        String email = "pepe@civica.com";

        // Act
        Submission result = inviteCandidateService.execute(name, email);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getCandidateName());
        assertEquals(email, result.getEmail());
        assertEquals(SubmissionStatus.PENDING_INVITE, result.getStatus());
        assertNotNull(result.getToken());

        // Verificar que se llamó a los puertos (salidas)
        verify(userIdentityPort).registerPendingInvite(eq(name), eq(email), anyString());
        verify(submissionRepository).save(any(Submission.class));
        verify(notificationPort).sendInvitation(eq(email), eq(name), anyString());
    }
}