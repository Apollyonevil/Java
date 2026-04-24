package com.civica.newhires.documents.domain.ports.input;

import com.civica.newhires.documents.domain.model.FileResource;

public interface UploadFileUseCase {
 
    FileResource execute(String originalName, String mimeType, Long size, byte[] content);
}