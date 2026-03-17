package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.application.dto.FieldResponseDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SubmitFormUseCase {
    void execute(String token, List<FieldResponseDTO> textResponses, Map<UUID, MultipartFile> files);
}