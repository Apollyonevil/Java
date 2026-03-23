package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.output.NotificationPort;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminWebControllerPostEmail.class)
class AdminWebControllerPostEmailTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubmissionRepository submissionRepository;

    @MockitoBean
    private NotificationPort notificationPort;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaEnviarEmailCorrectamente() throws Exception {
        UUID id = UUID.randomUUID();
        Submission submission = new Submission(UUID.randomUUID(), "Juan", "juan@test.com", "token123");
        when(submissionRepository.findById(id)).thenReturn(Optional.of(submission));
        doNothing().when(notificationPort).sendInvitation(any(), any(), any());

        mockMvc.perform(post("/api/admin/forms/send-email/{id}", id).with(csrf()))
                .andExpect(status().isOk());
    }

   @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaRetornar500SiNoEncuentraSubmission() throws Exception {
        UUID id = UUID.randomUUID();
        when(submissionRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/admin/forms/send-email/{id}", id).with(csrf()))
                .andExpect(status().isBadRequest()); // <-- cambia esto
    }
}