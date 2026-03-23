package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.model.SubmissionStatus;
import com.civica.newhires.forms.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.forms.domain.ports.output.NotificationPort;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminWebControllerPostInviteReject.class)
class AdminWebControllerPostInviteRejectTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SubmissionRepository submissionRepository;

    @MockitoBean
    private NotificationPort notificationPort;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaRechazarSubmissionYCambiarEstado() throws Exception {
        UUID id = UUID.randomUUID();
        Submission submission = new Submission(
            UUID.randomUUID(), "Juan García", "juan@test.com", "token123"
        );
        submission.setStatus(SubmissionStatus.SUBMITTED);

        when(submissionRepository.findById(id)).thenReturn(Optional.of(submission));
        when(submissionRepository.findByToken(any())).thenReturn(Optional.of(submission));

        mockMvc.perform(post("/api/admin/forms/submissions/{id}/reject", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AdminWebControllerPostInviteReject.RejectRequest("DNI caducado"))))
                .andExpect(status().isOk());
    }
}