package com.civica.newhires.submissions.application.dto;

import java.util.List;

import com.civica.newhires.fields.application.dto.FieldResponseDTO;

public record FormSubmissionRequest(
    String token,
    List<FieldResponseDTO> responses
) {}
