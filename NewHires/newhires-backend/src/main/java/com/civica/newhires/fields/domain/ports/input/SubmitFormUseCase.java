package com.civica.newhires.fields.domain.ports.input;

import com.civica.newhires.documents.application.dto.FileInput;
import com.civica.newhires.fields.application.dto.FieldResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SubmitFormUseCase {
    void execute(String token, List<FieldResponseDTO> textResponses, Map<UUID, FileInput> files);
}