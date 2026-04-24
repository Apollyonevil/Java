package com.civica.newhires.documents.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "file_resources")
public class FileResourceEntity {

    @Id
    @Column(name = "id", length = 36, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "size", nullable = false)
    private Long size;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }
}