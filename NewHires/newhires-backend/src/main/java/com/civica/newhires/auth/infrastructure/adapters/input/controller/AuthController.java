package com.civica.newhires.auth.infrastructure.adapters.input.controller;

import com.civica.newhires.auth.application.dto.LoginRequestDTO;
import com.civica.newhires.auth.application.dto.UserResponseDTO;
import com.civica.newhires.employee.infrastructure.persistence.repository.EmployeeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthController {

    private final EmployeeUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        return userRepository.findByUsername(principal.getName())
            .map(user -> ResponseEntity.ok(new UserResponseDTO(
                user.getId(), user.getUsername(), user.getRole().name()
            )))
            .orElse(ResponseEntity.status(404).build());
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return userRepository.findByUsername(request.username())
            .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
            .map(user -> ResponseEntity.ok(new UserResponseDTO(
                user.getId(), user.getUsername(), user.getRole().name()
            )))
            .orElse(ResponseEntity.status(401).build());
    }
}