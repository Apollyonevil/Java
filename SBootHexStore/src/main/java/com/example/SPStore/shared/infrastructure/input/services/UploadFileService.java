package com.example.SPStore.shared.infrastructure.input.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
    // Usamos Paths.get() para que sea compatible con cualquier sistema operativo
    private String folder = "images";

    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            // Creamos la ruta de forma segura
            Path path = Paths.get(folder).resolve(file.getOriginalFilename());
            
            // Aseguramos que el directorio existe
            File dir = new File(folder);
            if (!dir.exists()) dir.mkdirs();

            Files.write(path, bytes);
            return file.getOriginalFilename();
        } 
        return "default.jpg";
    }

    public void deleteImage(String name) {
        Path path = Paths.get(folder).resolve(name);
        File file = path.toFile();
        if (file.exists()) {
            file.delete();
        }
    }
}