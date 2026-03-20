package com.civica.newhires.shared.infrastructure.input.controllers;

import com.civica.newhires.shared.infrastructure.persistence.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


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