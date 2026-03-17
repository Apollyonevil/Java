package com.civica.newhires.forms.application.dto;

import com.civica.newhires.forms.domain.model.SubmissionStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record SubmissionResponseDTO(
    UUID id,
    UUID employeeId,
    String employeeFullName, // Esto lo sacarías uniendo datos de otro slice
    LocalDateTime submittedAt,
    SubmissionStatus status
) {}