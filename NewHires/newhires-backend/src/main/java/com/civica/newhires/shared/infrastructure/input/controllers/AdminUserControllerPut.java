package com.civica.newhires.shared.infrastructure.input.controllers;

import com.civica.newhires.shared.infrastructure.input.controllers.AdminUserControllerDelete.UserRequest;
import com.civica.newhires.shared.infrastructure.persistence.entities.AdminUserEntity;
import com.civica.newhires.shared.infrastructure.persistence.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminUserControllerPut {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/{id}")
    public ResponseEntity<AdminUserEntity> update(@PathVariable String id, @RequestBody UserRequest request) {
        return adminUserRepository.findById(id).map(user -> {
            user.setUsername(request.username());
            if (request.password() != null && !request.password().isBlank()) {
                user.setPassword(passwordEncoder.encode(request.password()));
            }
            return ResponseEntity.ok(adminUserRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }

}