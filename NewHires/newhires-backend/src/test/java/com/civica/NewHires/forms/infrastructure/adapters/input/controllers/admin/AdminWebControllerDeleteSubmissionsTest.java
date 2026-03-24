package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin.AdminWebControllerDeleteSubmissions;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminWebControllerDeleteSubmissions.class)
class AdminWebControllerDeleteSubmissionsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubmissionRepository submissionRepository;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaEliminarSubmissionCorrectamente() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(submissionRepository).deleteById(id);

        mockMvc.perform(delete("/api/admin/forms/submissions/{id}", id).with(csrf()))
                .andExpect(status().isNoContent());
    }
}