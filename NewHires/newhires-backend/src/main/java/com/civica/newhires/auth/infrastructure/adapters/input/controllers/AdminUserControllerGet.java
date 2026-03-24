package com.civica.newhires.auth.infrastructure.adapters.input.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.civica.newhires.auth.infrastructure.persistence.entities.AdminUserEntity;
import com.civica.newhires.auth.infrastructure.persistence.repository.AdminUserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminUserControllerGet {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<AdminUserEntity>> getAll() {
        return ResponseEntity.ok(adminUserRepository.findAll());
    }

}