package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin.AdminWebControllerPostInvite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminWebControllerPostInvite.class)
class AdminWebControllerPostInviteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InviteCandidateUseCase inviteCandidateUseCase;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaCrearInvitacionCorrectamente() throws Exception {
        Submission submission = new Submission(UUID.randomUUID(), UUID.randomUUID(), "token");
        when(inviteCandidateUseCase.execute("Juan García", "juan@test.com")).thenReturn(submission);

        mockMvc.perform(post("/api/admin/forms/invite")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"candidateName\":\"Juan García\",\"email\":\"juan@test.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.candidateName").value("Juan García"));
    }
}