package com.civica.newhires.auth.infrastructure.adapters.input.controllers;

import com.civica.newhires.auth.application.service.EmployeeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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