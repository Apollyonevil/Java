package com.civica.newhires.employee.infrastructure.adapters.input.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.civica.newhires.employee.application.dto.EmployeeDTO;
import com.civica.newhires.employee.application.service.EmployeeUserService;

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