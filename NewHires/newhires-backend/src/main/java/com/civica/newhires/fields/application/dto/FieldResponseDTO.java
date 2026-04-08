package com.civica.newhires.fields.application.dto;

import java.util.UUID;

public record FieldResponseDTO(
    UUID fieldDefinitionId,
    String value 
) {}