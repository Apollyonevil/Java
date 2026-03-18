package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.forms.domain.ports.input.InviteCandidateUseCase;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AdminFormController {

    private final GetSubmissionsUseCase getSubmissionsUseCase;
    private final InviteCandidateUseCase inviteCandidateUseCase;

    @GetMapping("/submissions")
    public List<Submission> getAll() {
        return getSubmissionsUseCase.execute();
    }

    @PostMapping("/invite")
    public Submission invite(@RequestBody InviteRequest request) {
        return inviteCandidateUseCase.execute(request.getCandidateName(), request.getEmail());
    }

    // Usamos anotaciones de Lombok para garantizar que Jackson pueda leer el JSON
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InviteRequest {
        private String candidateName;
        private String email;
    }
}