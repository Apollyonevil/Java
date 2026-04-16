package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.RenewTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostInviteRenew {

    private final RenewTokenUseCase renewTokenUseCase;

    @PostMapping("/submissions/{id}/renew")
    public ResponseEntity<Submission> renewToken(@PathVariable UUID id) {
        return ResponseEntity.ok(renewTokenUseCase.execute(id));
    }
}