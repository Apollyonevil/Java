package com.civica.newhires.documents.domain.ports.output;

import java.util.Optional;
import java.util.UUID;

import com.civica.newhires.documents.domain.model.FileResource;

public interface FileResourceRepository {
    FileResource save(FileResource fileResource);
    Optional<FileResource> findById(UUID id);
    void deleteById(UUID id);
}