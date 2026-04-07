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