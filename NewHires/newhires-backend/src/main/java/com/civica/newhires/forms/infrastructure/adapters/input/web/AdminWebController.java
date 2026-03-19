package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.Submission;
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

    // 1. DASHBOARD DATA (Para ver la tabla de candidatos)
    @GetMapping("/data")
    public ResponseEntity<List<Submission>> getDashboardData() {
        return ResponseEntity.ok(getSubmissionsUseCase.execute());
    }

    // 2. CREAR CAMPO
    @PostMapping("/fields")
    public ResponseEntity<FieldDefinition> saveField(@RequestBody FieldDefinition field) {
        return ResponseEntity.ok(manageFieldsUseCase.createField(field));
    }

    // 3. ACTUALIZAR CAMPO (Clave para Editar y para el Orden)
    @PutMapping("/fields/{id}")
    public ResponseEntity<FieldDefinition> updateField(@PathVariable UUID id, @RequestBody FieldDefinition field) {
        return ResponseEntity.ok(manageFieldsUseCase.updateField(id, field));
    }

    // 4. ELIMINAR CAMPO
    @DeleteMapping("/fields/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable UUID id) {
        manageFieldsUseCase.deleteField(id);
        return ResponseEntity.noContent().build();
    }

    // 5. ENVÍO DE EMAIL MANUAL
    @PostMapping("/send-email/{employeeId}")
    public ResponseEntity<Void> sendManualEmail(@PathVariable UUID employeeId) {
        Submission submission = submissionRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + employeeId));
        
        notificationPort.sendSubmissionConfirmation(
            submission.getEmail(), 
            submission.getCandidateName()
        );
        
        return ResponseEntity.ok().build();
    }
}