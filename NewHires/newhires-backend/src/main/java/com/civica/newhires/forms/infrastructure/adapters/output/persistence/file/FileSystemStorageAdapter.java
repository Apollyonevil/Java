package com.civica.newhires.forms.infrastructure.adapters.output.persistence.file;

import com.civica.newhires.forms.domain.ports.output.FileStoragePort;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystemStorageAdapter implements FileStoragePort {

    private final Path rootLocation;

    // Spring inyectará aquí el valor de tu application.properties
    public FileSystemStorageAdapter(@Value("${storage.location:uploads}") String storageLocation) {
        this.rootLocation = Paths.get(storageLocation);
        init(); // Crea la carpeta al arrancar
    }

    private void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el almacenamiento", e);
        }
    }

    @Override
    public String save(MultipartFile file, String newName) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Path destinationFile = this.rootLocation.resolve(newName);
        Files.write(destinationFile, file.getBytes());
        
        return newName; 
    }

    @Override
    public void delete(String fileName) {
        try {
            Files.deleteIfExists(this.rootLocation.resolve(fileName));
        } catch (IOException e) {
            // Loguear error si fuera necesario
        }
    }
}