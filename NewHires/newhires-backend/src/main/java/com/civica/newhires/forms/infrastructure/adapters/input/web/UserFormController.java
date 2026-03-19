package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.application.dto.FieldDefinitionDTO;
import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.application.dto.FileInput;
import com.civica.newhires.forms.domain.ports.input.GetFormStructureUseCase;
import com.civica.newhires.forms.domain.ports.input.SubmitFormUseCase;
import com.civica.newhires.forms.infrastructure.adapters.input.web.mappers.FieldDTOMapper;
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
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserFormController {

    private final SubmitFormUseCase submitFormUseCase;
    private final GetFormStructureUseCase getFormStructureUseCase;
    private final FieldDTOMapper mapper;

    @GetMapping("/structure")
    public ResponseEntity<List<FieldDefinitionDTO>> getStructure() {
        List<FieldDefinitionDTO> fields = getFormStructureUseCase.execute()
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(fields);
    }

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
                // no es un UUID, es token o responses, lo ignoramos
            }
        });

        submitFormUseCase.execute(token, responses, domainFiles);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}