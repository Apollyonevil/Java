package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.application.dto.FieldDefinitionDTO;
import com.civica.newhires.forms.domain.ports.input.SubmitFormUseCase;
import com.civica.newhires.forms.domain.ports.input.GetFormStructureUseCase;
import com.civica.newhires.forms.infrastructure.adapters.input.web.mappers.FieldDTOMapper; 
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class FormController {

    private final SubmitFormUseCase submitFormUseCase;
    private final GetFormStructureUseCase getFormStructureUseCase; // <--- AÑADE ESTO
    private final FieldDTOMapper mapper; // <--- AÑADE ESTO (tu mapper de web)
    private final ObjectMapper objectMapper; 

    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> submit(
            @RequestPart("token") String token,
            @RequestPart("responses") String responsesJson,
            @RequestPart(value = "files", required = false) Map<String, MultipartFile> filesMap
    ) throws Exception {
   
        List<FieldResponseDTO> responses = objectMapper.readValue(
            responsesJson, 
            new TypeReference<List<FieldResponseDTO>>() {}
        );

        java.util.Map<UUID, MultipartFile> uuidFiles = new java.util.HashMap<>();
        if (filesMap != null) {
            filesMap.forEach((key, file) -> uuidFiles.put(UUID.fromString(key), file));
        }

        submitFormUseCase.execute(token, responses, uuidFiles);
        
        return ResponseEntity.ok().build();
    }

@GetMapping 
public List<FieldDefinitionDTO> getFields() {
    return getFormStructureUseCase.execute()
            .stream()
            .map(mapper::toDTO)
            .toList();
}
}