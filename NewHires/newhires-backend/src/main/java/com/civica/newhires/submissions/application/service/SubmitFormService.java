package com.civica.newhires.submissions.application.service;

import com.civica.newhires.employee.domain.ports.output.UserIdentityPort;
import com.civica.newhires.fields.domain.service.FormDomainService;
import com.civica.newhires.fields.application.dto.FieldResponseDTO;
import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.model.FieldValue;
import com.civica.newhires.fields.domain.ports.input.SubmitFormUseCase;
import com.civica.newhires.fields.domain.ports.output.FormPort;
import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.candidates.domain.model.AccessToken;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;
import com.civica.newhires.submissions.domain.model.SubmissionStatusHistory;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.documents.application.dto.FileInput;
import com.civica.newhires.documents.domain.model.FileResource;
import com.civica.newhires.documents.domain.ports.input.UploadFileUseCase;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionStatusHistoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmitFormService implements SubmitFormUseCase {

    private final FormPort formRepository;
    private final SubmissionPort submissionRepository;
    private final AccessTokenPort accessTokenRepository;
    private final UserIdentityPort userIdentityPort;
    private final UploadFileUseCase uploadFileUseCase; 
    private final FormDomainService formDomainService;
    private final NotificationPort notificationPort;
    private final SubmissionStatusHistoryPort historyRepository;

    @Override
    @Transactional
    public void execute(String token, List<FieldResponseDTO> textResponses, Map<UUID, FileInput> files) {
        log.info("[SUBMIT] Iniciando procesamiento del formulario con token: {}", token);

        AccessToken accessToken = accessTokenRepository.findByToken(token)
                .filter(AccessToken::isValid)
                .orElseThrow(() -> new RuntimeException("Acceso no autorizado: Token inválido o caducado"));

        UUID employeeId = userIdentityPort.findEmployeeIdByToken(token)
                .orElseThrow(() -> new RuntimeException("No se encontró employeeId para el token"));


        Submission submission = submissionRepository.findById(accessToken.getSubmissionId())
                .orElseThrow(() -> new RuntimeException("No se encontró submission para el ID: " + accessToken.getSubmissionId()));

        List<FieldDefinition> definitions = formRepository.findAllFieldDefinitions();
        List<FieldValue> valuesToSave = new ArrayList<>();


        if (textResponses != null && !textResponses.isEmpty()) {
            log.info("[SUBMIT] Procesando {} respuestas de texto", textResponses.size());
            textResponses.forEach(dto -> {
                UUID fieldId = parseUuid(dto.fieldDefinitionId());
                FieldDefinition def = findDefinition(definitions, fieldId);
                formDomainService.validateField(def, dto.value());
                
                valuesToSave.add(new FieldValue(
                    def.getId(), 
                    employeeId, 
                    submission.getId(), 
                    dto.value(), 
                    null
                ));
            });
        }


        if (files != null && !files.isEmpty()) {
            log.info("[SUBMIT] Procesando {} archivos", files.size());
            files.forEach((fieldUuid, fileInput) -> {
                FieldDefinition def = findDefinition(definitions, fieldUuid);
                
                FileResource resource = uploadFileUseCase.execute(
                    fileInput.fileName(),
                    fileInput.contentType(),
                    (long) fileInput.content().length,
                    fileInput.content()
                );

                log.info("[SUBMIT] Archivo '{}' subido con ID recurso: {}", fileInput.fileName(), resource.getId());

                valuesToSave.add(new FieldValue(
                    def.getId(), 
                    employeeId, 
                    submission.getId(), 
                    null, 
                    resource.getId()
                ));
            });
        }

        if (!valuesToSave.isEmpty()) {
            log.info("[SUBMIT] Enviando {} registros a persistencia", valuesToSave.size());
            formRepository.saveValues(valuesToSave);
        }


        submission.markAsSubmitted(); 
        submissionRepository.save(submission);

        accessToken.markAsUsed();
        accessTokenRepository.save(accessToken);

        historyRepository.save(new SubmissionStatusHistory(
            submission.getId(),
            SubmissionStatus.SUBMITTED,
            "candidato"
        ));
        
        log.info("[SUBMIT] Formulario completado. Submission {} actualizada y token invalidado.", submission.getId());

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