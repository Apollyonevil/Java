package com.civica.newhires.employee.infrastructure.adapters.input.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.civica.newhires.employee.application.service.EmployeeUserService;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminUserControllerDelete {

    private final EmployeeUserService employeeUserService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        employeeUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}