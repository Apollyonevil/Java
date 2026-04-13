package com.civica.newhires.documents.infrastructure.adapters.output.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.civica.newhires.documents.domain.ports.output.FileStoragePort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystemStorageAdapter implements FileStoragePort {

    private final Path rootLocation;

    public FileSystemStorageAdapter(@Value("${storage.location:uploads}") String storageLocation) {
        this.rootLocation = Paths.get(storageLocation);
        init(); 
    }

    private void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el almacenamiento", e);
        }
    }

    @Override
    public String save(byte[] content, String newName) throws IOException {
        if (content == null || content.length == 0) {
            return null;
        }

        Path destinationFile = this.rootLocation.resolve(newName);
        Files.write(destinationFile, content);
        
        return destinationFile.toAbsolutePath().toString(); // ← cambio aquí
    }

    @Override
    public void delete(String fileName) {
        try {
            Files.deleteIfExists(this.rootLocation.resolve(fileName));
        } catch (IOException e) {
     
        }
    }
}