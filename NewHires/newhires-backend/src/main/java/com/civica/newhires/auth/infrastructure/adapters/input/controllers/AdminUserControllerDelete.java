package com.civica.newhires.auth.infrastructure.adapters.input.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.civica.newhires.auth.infrastructure.persistence.repository.AdminUserRepository;


@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminUserControllerDelete {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        adminUserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public record UserRequest(String username, String password) {}
}