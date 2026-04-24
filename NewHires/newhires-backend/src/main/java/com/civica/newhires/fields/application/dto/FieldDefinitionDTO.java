package com.civica.newhires.fields.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDefinitionDTO {
    private UUID id;
    private String label;
    private String type;
    private boolean required;
    private String placeholder;
    private List<String> options; 
    private Integer sortOrder; 
    private boolean active; 
}