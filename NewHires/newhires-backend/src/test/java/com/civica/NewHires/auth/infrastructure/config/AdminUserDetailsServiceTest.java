package com.civica.newhires.auth.infrastructure.config;

import com.civica.newhires.auth.infrastructure.persistence.entities.AdminUserEntity;
import com.civica.newhires.auth.infrastructure.persistence.repository.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminUserDetailsServiceTest {

    @Mock private AdminUserRepository adminUserRepository;

    @InjectMocks private AdminUserDetailsService adminUserDetailsService;

    @Test
    void debeCargarUsuarioCorrectamentePorUsername() {
        // Arrange
        AdminUserEntity entity = new AdminUserEntity();
        entity.setUsername("admin");
        entity.setPassword("hash123");
        
        when(adminUserRepository.findByUsername("admin")).thenReturn(Optional.of(entity));

        // Act
        UserDetails userDetails = adminUserDetailsService.loadUserByUsername("admin");

        // Assert
        assertEquals("admin", userDetails.getUsername());
        assertEquals("hash123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void debeLanzarExcepcionSiUsuarioNoExiste() {
        when(adminUserRepository.findByUsername("fantasma")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, 
            () -> adminUserDetailsService.loadUserByUsername("fantasma"));
    }
}