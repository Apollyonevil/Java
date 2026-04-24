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
public class AdminUserControllerPut {

    private final EmployeeUserService employeeUserService;

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable String id, @RequestBody EmployeeRequest request) {
        try {
            EmployeeDTO updatedUser = employeeUserService.update(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}