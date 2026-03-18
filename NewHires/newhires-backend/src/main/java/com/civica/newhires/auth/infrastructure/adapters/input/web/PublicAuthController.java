package com.civica.newhires.auth.infrastructure.adapters.input.web;

import com.civica.newhires.auth.domain.ports.input.ValidateAccessUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/public/auth") 
@RequiredArgsConstructor
public class PublicAuthController {

    private final ValidateAccessUseCase validateAccessUseCase;

    @GetMapping("/verify")
    public ResponseEntity<Void> verify(@RequestParam String token) {
        boolean isValid = validateAccessUseCase.execute(token);
        
        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}