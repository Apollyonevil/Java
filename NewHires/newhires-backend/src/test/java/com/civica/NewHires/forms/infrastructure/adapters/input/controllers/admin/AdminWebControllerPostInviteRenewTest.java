package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin.AdminWebControllerPostInviteRenew;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminWebControllerPostInviteRenew.class)
class AdminWebControllerPostInviteRenewTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubmissionRepository submissionRepository;

    @MockitoBean
    private NotificationPort notificationPort;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaRenovarTokenCorrectamente() throws Exception {
        UUID id = UUID.randomUUID();
        Submission submission = new Submission(UUID.randomUUID(), "Juan", "juan@test.com", "token-viejo");
        when(submissionRepository.findById(id)).thenReturn(Optional.of(submission));
        doNothing().when(notificationPort).sendInvitation(any(), any(), any());

        mockMvc.perform(post("/api/admin/forms/submissions/{id}/renew", id).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@test.com"));
    }
}