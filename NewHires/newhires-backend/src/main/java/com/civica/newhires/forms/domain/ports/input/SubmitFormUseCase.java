package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import com.civica.newhires.forms.application.dto.FileInput;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SubmitFormUseCase {
    void execute(String token, List<FieldResponseDTO> textResponses, Map<UUID, FileInput> files);
}