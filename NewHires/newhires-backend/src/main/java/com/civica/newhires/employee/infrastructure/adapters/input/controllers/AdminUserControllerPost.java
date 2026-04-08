package com.civica.newhires.employee.infrastructure.adapters.input.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.civica.newhires.employee.application.dto.EmployeeDTO;
import com.civica.newhires.employee.application.dto.EmployeeRequest;
import com.civica.newhires.employee.application.service.EmployeeUserService;

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