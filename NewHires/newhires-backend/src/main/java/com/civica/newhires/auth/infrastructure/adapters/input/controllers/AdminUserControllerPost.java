package com.civica.newhires.auth.infrastructure.adapters.input.controllers;

import com.civica.newhires.auth.infrastructure.adapters.input.controllers.AdminUserControllerDelete.UserRequest;
import com.civica.newhires.auth.infrastructure.persistence.entities.AdminUserEntity;
import com.civica.newhires.auth.infrastructure.persistence.repository.AdminUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminUserControllerPost {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<AdminUserEntity> create(@RequestBody UserRequest request) {
        AdminUserEntity user = new AdminUserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(true);
        return ResponseEntity.ok(adminUserRepository.save(user));
    }

}