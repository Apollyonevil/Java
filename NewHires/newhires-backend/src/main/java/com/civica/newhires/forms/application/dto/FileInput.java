package com.civica.newhires.forms.application.dto;

public record FileInput(
    String fileName,
    String contentType,
    byte[] content
) {}