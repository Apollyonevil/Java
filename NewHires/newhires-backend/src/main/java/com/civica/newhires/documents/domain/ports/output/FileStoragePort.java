package com.civica.newhires.documents.domain.ports.output;

import java.io.IOException;

public interface FileStoragePort {
    String save(byte[] content, String customName) throws IOException;
    
    void delete(String fileName);
}