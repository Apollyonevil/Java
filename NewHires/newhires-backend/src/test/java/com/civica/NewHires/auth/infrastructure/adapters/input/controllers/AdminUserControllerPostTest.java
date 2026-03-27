package com.civica.newhires.auth.infrastructure.adapters.input.controllers;

import com.civica.newhires.auth.infrastructure.persistence.entities.AdminUserEntity;
import com.civica.newhires.auth.infrastructure.persistence.repository.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminUserControllerPost.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminUserControllerPostTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminUserRepository adminUserRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void debeCrearUsuarioAdminCorrectamente() throws Exception {
        // Arrange
        String jsonRequest = "{\"username\": \"manuel\", \"password\": \"secret123\"}";
        
        AdminUserEntity savedUser = new AdminUserEntity();
        savedUser.setUsername("manuel");
        savedUser.setEnabled(true);

        when(passwordEncoder.encode("secret123")).thenReturn("encoded_pass");
        when(adminUserRepository.save(any(AdminUserEntity.class))).thenReturn(savedUser);

        // Act & Assert
        mockMvc.perform(post("/api/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("manuel"))
                .andExpect(jsonPath("$.enabled").value(true));
    }
}