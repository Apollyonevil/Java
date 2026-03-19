package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.forms.domain.ports.input.InviteCandidateUseCase;
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
        System.out.println("DEBUG: Invitando a " + request.getCandidateName());

        return inviteCandidateUseCase.execute(request.getCandidateName(), request.getEmail());
    }

    public static class InviteRequest {
        private String candidateName;
        private String email;
        public InviteRequest() {}
        public String getCandidateName() { return candidateName; }
        public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}