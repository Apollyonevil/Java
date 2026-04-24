package com.civica.newhires.notification.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.ports.input.SendInvitationEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostEmail {

    private final SendInvitationEmailUseCase sendInvitationEmailUseCase;

    @PostMapping("/send-email/{id}")
    public ResponseEntity<Void> sendManualEmail(@PathVariable UUID id) {
    
        sendInvitationEmailUseCase.send(id); 
        return ResponseEntity.ok().build();
    }
}