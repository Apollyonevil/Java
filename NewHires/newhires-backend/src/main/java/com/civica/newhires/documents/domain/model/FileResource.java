package com.civica.newhires.documents.domain.model;

import java.util.UUID;

public class FileResource {
    private final UUID id;
    private final String path;
    private final String originalName;
    private final String mimeType;
    private final Long size;

    public FileResource(String path, String originalName, String mimeType, Long size) {
        this.id = UUID.randomUUID();
        this.path = path;
        this.originalName = originalName;
        this.mimeType = mimeType;
        this.size = size;
    }

    public FileResource(UUID id, String path, String originalName, String mimeType, Long size) {
        this.id = id;
        this.path = path;
        this.originalName = originalName;
        this.mimeType = mimeType;
        this.size = size;
    }

    public UUID getId() { return id; }
    public String getPath() { return path; }
    public String getOriginalName() { return originalName; }
    public String getMimeType() { return mimeType; }
    public Long getSize() { return size; }
}