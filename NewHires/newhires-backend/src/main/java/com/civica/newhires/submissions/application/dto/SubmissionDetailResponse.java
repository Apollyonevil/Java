package com.civica.newhires.submissions.application.dto;

import java.util.List;

public record SubmissionDetailResponse(
    String candidateName,
    String email,
    String status,
    List<FieldValueDTO> fields
) {
    public record FieldValueDTO(
        String label,
        String value,
        boolean isFile,
        String type
    ) {}
}