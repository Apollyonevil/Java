package com.civica.newhires.submissions.application.service;

import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.forms.domain.ports.output.FormPort;
import com.civica.newhires.forms.domain.service.FormDomainService;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitFormServiceTest {

    @Mock private SubmissionRepository submissionRepository;
    @Mock private UserIdentityPort userIdentityPort;
    @Mock private FormPort formRepository;
    @Mock private NotificationPort notificationPort;
    @Mock private FormDomainService formDomainService;

    @InjectMocks private SubmitFormService submitFormService;

    @Test
    void debeCambiarEstadoASubmittedAlEnviarFormulario() {
        // Arrange
        String token = "token-secreto";
        UUID employeeId = UUID.randomUUID();
        Submission submission = new Submission(employeeId, "Juan", "juan@test.com", token);
        submission.setStatus(SubmissionStatus.PENDING_INVITE);

        when(userIdentityPort.findEmployeeIdByToken(token)).thenReturn(Optional.of(employeeId));
        when(userIdentityPort.findEmailByToken(token)).thenReturn(Optional.of("juan@test.com"));
        when(submissionRepository.findByToken(token)).thenReturn(Optional.of(submission));

        // Act
        submitFormService.execute(token, null, null); // Enviamos sin respuestas ni archivos para simplificar

        // Assert
        assertEquals(SubmissionStatus.SUBMITTED, submission.getStatus());
        verify(submissionRepository).save(submission);
        verify(notificationPort).sendSubmissionConfirmation(anyString(), anyString());
    }
}