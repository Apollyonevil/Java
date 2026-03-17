package com.civica.newhires.forms.application.dto;

import java.util.UUID;

public record FieldResponseDTO(
    UUID fieldDefinitionId,
    String value 
) {}