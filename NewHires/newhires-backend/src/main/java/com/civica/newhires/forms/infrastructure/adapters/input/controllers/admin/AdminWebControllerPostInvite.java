package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.InviteCandidateUseCase;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostInvite {

    private final InviteCandidateUseCase inviteCandidateUseCase;
    
    public static class InviteRequest {
        private String candidateName;
        private String email;
        
        public InviteRequest() {}
        public String getCandidateName() { return candidateName; }
        public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
   

    @PostMapping("/invite")
    public ResponseEntity<Submission> invite(@RequestBody InviteRequest request) {
        Submission submission = inviteCandidateUseCase.execute(request.getCandidateName(), request.getEmail());
        return ResponseEntity.ok(submission);
    }


}