package com.civica.newhires.auth.infrastructure.adapters.input.controllers;

import com.civica.newhires.auth.application.dto.EmployeeDTO;
import com.civica.newhires.auth.application.dto.EmployeeRequest;
import com.civica.newhires.auth.application.service.EmployeeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminUserControllerPost {

    private final EmployeeUserService employeeUserService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeRequest request) {
        EmployeeDTO createdUser = employeeUserService.create(request);
        
        return ResponseEntity.ok(createdUser);
    }
}