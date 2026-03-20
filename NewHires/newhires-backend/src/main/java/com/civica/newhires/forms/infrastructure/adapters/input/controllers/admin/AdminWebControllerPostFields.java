package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldType;
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
public class AdminWebControllerPostFields {

    private final ManageFieldsUseCase manageFieldsUseCase;
    private final GetSubmissionsUseCase getSubmissionsUseCase;
    private final SubmissionRepository submissionRepository;
    private final NotificationPort notificationPort;
    private final InviteCandidateUseCase inviteCandidateUseCase; // <-- añadido

    public record FieldRequest(
    String label,
    String type,
    boolean required,
    String placeholder,
    List<String> options,
    Integer sortOrder
    ) {}

@PostMapping("/fields")
public ResponseEntity<FieldDefinition> saveField(@RequestBody FieldRequest request) {
    FieldDefinition field = new FieldDefinition(
        null,
        request.label(),
        FieldType.valueOf(request.type()),
        request.required(),
        request.placeholder(),
        request.options(),
        request.sortOrder()
    );
    
    return ResponseEntity.ok(manageFieldsUseCase.createField(field));
}

}