package com.civica.newhires.forms.domain.ports.output;

import com.civica.newhires.forms.domain.model.FileResource;
import java.util.Optional;
import java.util.UUID;

public interface FileResourceRepository {
    FileResource save(FileResource fileResource);
    Optional<FileResource> findById(UUID id);
    void deleteById(UUID id);
}