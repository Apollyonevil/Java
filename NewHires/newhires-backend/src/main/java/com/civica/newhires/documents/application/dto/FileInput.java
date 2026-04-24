package com.civica.newhires.documents.application.dto;

public record FileInput(
    String fileName,
    String contentType,
    byte[] content
) {}