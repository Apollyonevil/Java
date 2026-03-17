package com.civica.newhires.forms.domain.ports.output;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileStoragePort {
    String save(MultipartFile file, String customName) throws IOException;
    void delete(String fileName);
}