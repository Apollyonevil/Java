package com.civica.newhires.submissions.application.service;

import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.application.dto.FileInput;
import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue;
import com.civica.newhires.forms.domain.ports.input.SubmitFormUseCase;
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import com.civica.newhires.forms.domain.ports.output.FileStoragePort;
import com.civica.newhires.forms.domain.service.FormDomainService;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmitFormService implements SubmitFormUseCase {

    private final FormRepository formRepository; 
    private final SubmissionRepository submissionRepository; 
    private final UserIdentityPort userIdentityPort;
    private final FileStoragePort fileStoragePort;
    private final FormDomainService formDomainService; 
    private final NotificationPort notificationPort;

    @Override
    @Transactional
    public void execute(String token, List<FieldResponseDTO> textResponses, Map<UUID, FileInput> files) {
        
        UUID employeeId = userIdentityPort.findEmployeeIdByToken(token)
                .orElseThrow(() -> new RuntimeException("Acceso no autorizado: Token inválido"));

        String employeeEmail = userIdentityPort.findEmailByToken(token)
                .orElseThrow(() -> new RuntimeException("No se encontró email para el token suministrado"));

        String employeeName = userIdentityPort.findNameByToken(token)
                .orElse(employeeEmail.split("@")[0]); 

        List<FieldDefinition> definitions = formRepository.findAllFieldDefinitions();
        List<FieldValue> valuesToSave = new ArrayList<>();

        if (textResponses != null) {
            textResponses.forEach(dto -> {
                UUID fieldId = parseUuid(dto.fieldDefinitionId());
                FieldDefinition def = findDefinition(definitions, fieldId);
                
                formDomainService.validateField(def, dto.value());
                valuesToSave.add(new FieldValue(def.getId(), employeeId, dto.value()));
            });
        }

        if (files != null) {
            files.forEach((fieldUuid, fileInput) -> {
                FieldDefinition def = findDefinition(definitions, fieldUuid);
                
                try {
                    String newName = formDomainService.generateFileName(def.getLabel(), fileInput.fileName());
                    String savedPath = fileStoragePort.save(fileInput.content(), newName);
                    valuesToSave.add(new FieldValue(def.getId(), employeeId, savedPath));
                } catch (IOException e) {
                    throw new RuntimeException("Error al procesar el archivo: " + def.getLabel(), e);
                }
            });
        }

        formRepository.saveValues(valuesToSave);

        submissionRepository.findByToken(token).ifPresentOrElse(
            existing -> {
                existing.setStatus(SubmissionStatus.SUBMITTED);
                submissionRepository.save(existing);
            },
            () -> {
                Submission submission = new Submission(employeeId, employeeName, employeeEmail, token);
                submission.setStatus(SubmissionStatus.SUBMITTED);
                submissionRepository.save(submission);
            }
        );

        try {
            notificationPort.sendSubmissionConfirmation(employeeEmail, employeeName);
            Thread.sleep(1500);
            notificationPort.sendAdminNotification("rrhh@civica.com", employeeName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("⚠️ Email no enviado: " + e.getMessage());
        }
    }

    private UUID parseUuid(Object id) {
        if (id instanceof UUID) return (UUID) id;
        return UUID.fromString(id.toString());
    }

    private FieldDefinition findDefinition(List<FieldDefinition> definitions, UUID id) {
        return definitions.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Definición de campo no encontrada: " + id));
    }
}