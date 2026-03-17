package com.civica.newhires.forms.application.service;

import org.springframework.stereotype.Component;

@Component
public class FileNameGenerator {
    public String generate(String label, String apellido, String nombre, String originalName) {
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        // Ejemplo: DNI_AVILA_MANUEL.pdf
        return (label + "_" + apellido + "_" + nombre + extension)
                .toUpperCase()
                .replace(" ", "_");
    }
}