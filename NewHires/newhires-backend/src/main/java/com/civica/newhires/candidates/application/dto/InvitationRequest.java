package com.civica.newhires.candidates.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record InvitationRequest(
    String candidateName,
    String email,
    
    @JsonProperty("employee_id") 
    UUID employeeId 
) {}