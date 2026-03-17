package com.civica.newhires.forms.application.dto;

import java.util.List;

public record FormSubmissionRequest(
    String token,
    List<FieldResponseDTO> responses
) {}
