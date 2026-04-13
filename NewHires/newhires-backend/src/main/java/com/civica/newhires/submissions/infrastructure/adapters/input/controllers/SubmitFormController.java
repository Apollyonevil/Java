package com.civica.newhires.submissions.infrastructure.adapters.input.controllers;

import com.civica.newhires.documents.application.dto.FileInput;
import com.civica.newhires.fields.application.dto.FieldResponseDTO;
import com.civica.newhires.fields.domain.ports.input.SubmitFormUseCase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class SubmitFormController {

    private final SubmitFormUseCase submitFormUseCase;
    private final ObjectMapper objectMapper;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submit(
            @RequestParam("token") String token,
            @RequestPart("responses") String responsesJson,
            HttpServletRequest request) {

        log.info("--- [CONTROLLER] INICIO SUBMIT ---");
        log.info("Token: {}", token);

        try {
            List<FieldResponseDTO> textResponses = objectMapper.readValue(
                    responsesJson,
                    new TypeReference<List<FieldResponseDTO>>() {}
            );
            log.info("Respuestas de texto detectadas: {}", textResponses.size());

            Map<UUID, FileInput> filesToProcess = new HashMap<>();

            for (Part part : request.getParts()) {
                String partName = part.getName();
                log.info("[CONTROLLER] Part recibida: {}", partName);

                if (partName.startsWith("files[")) {
                    try {
                        String uuidClean = partName.substring(
                            partName.indexOf("[") + 1, partName.indexOf("]")
                        );
                        UUID fieldId = UUID.fromString(uuidClean);

                        byte[] bytes = part.getInputStream().readAllBytes();
                        String contentType = part.getContentType();
                        String fileName = part.getSubmittedFileName();

                        if (bytes.length > 0) {
                            filesToProcess.put(fieldId, new FileInput(fileName, contentType, bytes));
                            log.info("[CONTROLLER] Archivo mapeado: {} → campo {}", fileName, fieldId);
                        }
                    } catch (Exception e) {
                        log.warn("[CONTROLLER] No se pudo procesar la parte '{}': {}", partName, e.getMessage());
                    }
                }
            }

            log.info("Total archivos para procesar: {}", filesToProcess.size());
            submitFormUseCase.execute(token, textResponses, filesToProcess);

            return ResponseEntity.ok(Map.of("message", "Formulario recibido con éxito"));

        } catch (IOException e) {
            log.error("Error de lectura: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Error en el formato de los datos"));
        } catch (Exception e) {
            log.error("Error crítico: ", e);
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}