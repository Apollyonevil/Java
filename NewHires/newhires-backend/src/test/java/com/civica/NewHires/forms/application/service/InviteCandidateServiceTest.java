package com.civica.newhires.forms.application.service;

import com.civica.newhires.submissions.application.service.InviteCandidateService;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import com.civica.newhires.submissions.domain.ports.output.CandidateRepository;
import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InviteCandidateServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private UserIdentityPort userIdentityPort;

    @Mock
    private com.civica.newhires.submissions.domain.ports.output.NotificationPort notificationPort;

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private InviteCandidateService inviteCandidateService;

    @Test
    void deberiaCrearInvitacionConEstadoPendingInvite() {
        when(candidateRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Submission result = inviteCandidateService.execute("Juan García", "juan@test.com");

        assertNotNull(result);
        assertNotNull(result.getCandidateId());
        assertNotNull(result.getEmployeeId());
        assertEquals(SubmissionStatus.PENDING_INVITE, result.getStatus());
        assertNotNull(result.getToken());
    }
    @Test
    void deberiaGuardarLaSubmission() {
        inviteCandidateService.execute("Juan García", "juan@test.com");
        verify(submissionRepository, times(1)).save(any(Submission.class));
    }

    @Test
    void deberiaEnviarEmailDeInvitacion() {
        inviteCandidateService.execute("Juan García", "juan@test.com");
        verify(notificationPort, times(1)).sendInvitation(
            eq("juan@test.com"),
            eq("Juan García"),
            anyString()
        );
    }
}