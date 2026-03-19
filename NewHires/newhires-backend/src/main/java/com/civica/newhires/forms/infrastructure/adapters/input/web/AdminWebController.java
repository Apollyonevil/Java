package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import com.civica.newhires.forms.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.forms.domain.ports.output.NotificationPort;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebController {

    private final ManageFieldsUseCase manageFieldsUseCase;
    private final GetSubmissionsUseCase getSubmissionsUseCase;
    private final SubmissionRepository submissionRepository;
    private final NotificationPort notificationPort;
    private final InviteCandidateUseCase inviteCandidateUseCase; // <-- añadido

    @GetMapping("/submissions")
    public ResponseEntity<List<Submission>> getAll() {
        return ResponseEntity.ok(getSubmissionsUseCase.execute());
    }

    @GetMapping("/data")
    public ResponseEntity<List<Submission>> getDashboardData() {
        return ResponseEntity.ok(getSubmissionsUseCase.execute());
    }

    @PostMapping("/invite")
    public ResponseEntity<Submission> invite(@RequestBody InviteRequest request) {
        Submission submission = inviteCandidateUseCase.execute(request.getCandidateName(), request.getEmail());
        return ResponseEntity.ok(submission);
    }

    @PostMapping("/fields")
    public ResponseEntity<FieldDefinition> saveField(@RequestBody FieldDefinition field) {
        return ResponseEntity.ok(manageFieldsUseCase.createField(field));
    }

    @PutMapping("/fields/{id}")
    public ResponseEntity<FieldDefinition> updateField(@PathVariable UUID id, @RequestBody FieldDefinition field) {
        return ResponseEntity.ok(manageFieldsUseCase.updateField(id, field));
    }

    @DeleteMapping("/fields/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable UUID id) {
        manageFieldsUseCase.deleteField(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send-email/{employeeId}")
    public ResponseEntity<Void> sendManualEmail(@PathVariable UUID employeeId) {
        Submission submission = submissionRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + employeeId));
        
        notificationPort.sendInvitation(
            submission.getEmail(),
            submission.getCandidateName(),
            submission.getToken()
        );
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/submissions/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable UUID id) {
        submissionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
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