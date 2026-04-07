package com.civica.newhires.auth.infrastructure.adapters.input.controllers;

import com.civica.newhires.auth.application.dto.EmployeeDTO;
import com.civica.newhires.auth.application.service.EmployeeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminUserControllerGet {

    private final EmployeeUserService employeeUserService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        return ResponseEntity.ok(employeeUserService.getAllUsers());
    }
}