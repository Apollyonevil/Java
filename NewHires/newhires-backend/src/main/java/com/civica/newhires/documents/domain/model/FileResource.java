package com.civica.newhires.documents.domain.model;

import java.util.UUID;

public class FileResource {
    private final UUID id;
    private final String originalName;
    private final String mimeType;
    private final Long size;
    private final String path;

    public FileResource(UUID id, String originalName, String mimeType, Long size, String path) {
        this.id = id;
        this.originalName = originalName;
        this.mimeType = mimeType;
        this.size = size;
        this.path = path;
    }

    // Getters
    public UUID getId() { return id; }
    public String getOriginalName() { return originalName; }
    public String getMimeType() { return mimeType; }
    public Long getSize() { return size; }
    public String getPath() { return path; }
}