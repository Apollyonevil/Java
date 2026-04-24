package com.civica.newhires.fields.infrastructure.adapters.input.controllers.user;

import com.civica.newhires.documents.application.dto.FileInput;
import com.civica.newhires.fields.application.dto.FieldResponseDTO;
import com.civica.newhires.fields.domain.ports.input.SubmitFormUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor

public class UserFormControllerPost {

    private final SubmitFormUseCase submitFormUseCase;

    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> submit(
            @RequestPart("token") String token,
            @RequestPart("responses") List<FieldResponseDTO> responses,
            @RequestParam Map<String, MultipartFile> allParams) throws IOException {

        Map<UUID, FileInput> domainFiles = new HashMap<>();

        allParams.forEach((key, file) -> {
            try {
                UUID uuid = UUID.fromString(key);
                if (file != null && !file.isEmpty()) {
                    try {
                        domainFiles.put(uuid, new FileInput(
                            file.getOriginalFilename(),
                            file.getContentType(),
                            file.getBytes()
                        ));
                    } catch (IOException e) {
                        throw new RuntimeException("Error leyendo archivo", e);
                    }
                }
            } catch (IllegalArgumentException e) {
            
            }
        });

        submitFormUseCase.execute(token, responses, domainFiles);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}