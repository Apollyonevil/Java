package com.civica.newhires.auth.infrastructure.adapters.input.web;

import com.civica.newhires.auth.application.dto.InvitationRequest;
import com.civica.newhires.auth.domain.ports.input.SendInvitationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth") 
@RequiredArgsConstructor
public class AdminAuthController {

    private final SendInvitationUseCase sendInvitationUseCase;

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(@RequestBody InvitationRequest request) {
        sendInvitationUseCase.execute(request.email());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}