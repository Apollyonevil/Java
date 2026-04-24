package com.civica.newhires.candidates.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.candidates.domain.ports.input.InviteCandidateUseCase;
import com.fasterxml.jackson.annotation.JsonProperty; 

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerPostInvite {

    private final InviteCandidateUseCase inviteCandidateUseCase;

    @PostMapping("/invite")
    public ResponseEntity<Submission> invite(@RequestBody InviteRequest request) {
        // Usamos los getters del objeto interno para pasar los datos al caso de uso
        Submission submission = inviteCandidateUseCase.execute(
            request.getCandidateName(), 
            request.getEmail(),
            request.getEmployeeId()
        );
        
        return ResponseEntity.ok(submission);
    }

    // --- AQUÍ VA LA CLASE INTERNA ---
    public static class InviteRequest {
        private String candidateName;
        private String email;

        //@JsonProperty("employee_id") // Esto soluciona el error del JSON "employee_id"
        private UUID employeeId;

        public InviteRequest() {}

        // Getters y Setters manuales para evitar fallos de Lombok en el DTO
        public String getCandidateName() { return candidateName; }
        public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public UUID getEmployeeId() { return employeeId; }
        public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    }
}