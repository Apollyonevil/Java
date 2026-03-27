package com.civica.newhires.auth.application.service;

import com.civica.newhires.auth.application.dto.AdminUserDTO;
import com.civica.newhires.auth.application.dto.AdminUserRequest;
import com.civica.newhires.auth.infrastructure.persistence.entities.AdminUserEntity;
import com.civica.newhires.auth.infrastructure.persistence.repository.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminUserServiceTest {

    @Mock private AdminUserRepository repository;
    @Mock private PasswordEncoder passwordEncoder;
    
    @InjectMocks private AdminUserService adminUserService;

    @Test
    void debeCrearUsuarioCifrandoPassword() {
        // Arrange
        AdminUserRequest request = new AdminUserRequest("nuevo_admin", "pass123");
        when(repository.findByUsername("nuevo_admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass123")).thenReturn("hash_encriptado");
        when(repository.save(any(AdminUserEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        AdminUserDTO result = adminUserService.create(request);

        // Assert
        assertEquals("nuevo_admin", result.username());
        assertTrue(result.enabled());
        assertNotNull(result.id());
        verify(passwordEncoder).encode("pass123");
        verify(repository).save(any(AdminUserEntity.class));
    }

    @Test
    void debeLanzarExcepcionSiSeCreaUsuarioQueYaExiste() {
        // Arrange
        AdminUserRequest request = new AdminUserRequest("admin_repe", "123");
        when(repository.findByUsername("admin_repe")).thenReturn(Optional.of(new AdminUserEntity()));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> adminUserService.create(request));
        verify(repository, never()).save(any());
    }

    @Test
    void debeActualizarPasswordSoloSiSeProporciona() {
        // Arrange
        String id = UUID.randomUUID().toString();
        AdminUserEntity existingUser = new AdminUserEntity();
        existingUser.setId(id);
        existingUser.setUsername("user");
        existingUser.setPassword("old_hash");

        AdminUserRequest request = new AdminUserRequest("user_editado", "new_password");
        
        when(repository.findById(id)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("new_password")).thenReturn("new_hash");
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // Act
        AdminUserServiceTest.this.adminUserService.update(id, request);

        // Assert
        verify(passwordEncoder).encode("new_password");
        verify(repository).save(existingUser);
    }
}