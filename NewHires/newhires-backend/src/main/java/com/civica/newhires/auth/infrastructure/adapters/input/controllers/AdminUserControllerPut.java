package com.civica.newhires.auth.infrastructure.adapters.input.controllers;

import com.civica.newhires.auth.application.dto.AdminUserDTO;
import com.civica.newhires.auth.application.dto.AdminUserRequest;
import com.civica.newhires.auth.application.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminUserControllerPut {

    private final AdminUserService adminUserService;

    @PutMapping("/{id}")
    public ResponseEntity<AdminUserDTO> update(@PathVariable String id, @RequestBody AdminUserRequest request) {
        try {
            AdminUserDTO updatedUser = adminUserService.update(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}