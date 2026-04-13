package com.civica.newhires.submissions.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import com.civica.newhires.submissions.domain.model.SubmissionStatus;

public record SubmissionResponseDTO(
    UUID id,                 
    String candidateName,    
    String email,           
    UUID employeeId,        
    String employeeFullName, 
    LocalDateTime createdAt, 
    LocalDateTime submittedAt, 
    SubmissionStatus status,
    String token
) {}