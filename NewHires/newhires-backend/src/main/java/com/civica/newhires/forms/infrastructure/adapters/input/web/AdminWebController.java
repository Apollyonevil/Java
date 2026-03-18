package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import com.civica.newhires.forms.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.forms.domain.ports.output.NotificationPort; // Añadir
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository; // Añadir
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminWebController {

    private final ManageFieldsUseCase manageFieldsUseCase;
    private final GetSubmissionsUseCase getSubmissionsUseCase;
    
    // Necesitamos estos dos para el envío de emails
    private final SubmissionRepository submissionRepository; 
    private final NotificationPort notificationPort;

    // Dashboard: Listar envíos y campos
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("submissions", getSubmissionsUseCase.execute());
        return "admin/dashboard";
    }

    // --- GESTIÓN DE CAMPOS ---

    @GetMapping("/fields/new")
    public String showCreateFieldForm(Model model) {
        model.addAttribute("field", new FieldDefinition(null, "", null, false, "", null));
        return "admin/field-form";
    }

    @PostMapping("/fields")
    public String saveField(@ModelAttribute FieldDefinition field) {
        manageFieldsUseCase.createField(field);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/fields/delete/{id}")
    public String deleteField(@PathVariable UUID id) {
        manageFieldsUseCase.deleteField(id);
        return "redirect:/admin/dashboard";
    }

    // --- ENVÍO DE EMAIL ---
    
        @PostMapping("/send-email/{employeeId}")
        @ResponseBody
        public ResponseEntity<Void> sendManualEmail(@PathVariable UUID employeeId) {
            
            // Forzamos que el Optional sea de tipo <Submission>
            Submission submission = submissionRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("No se encontró el registro: " + employeeId));
            
            // Ahora 'submission' ya es del tipo correcto y detectará los métodos
            notificationPort.sendSubmissionConfirmation(
                submission.getEmail(), 
                submission.getCandidateName()
            );
            
            return ResponseEntity.ok().build();
        }
}