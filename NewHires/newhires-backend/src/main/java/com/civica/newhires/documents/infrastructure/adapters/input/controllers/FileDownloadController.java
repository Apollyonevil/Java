package com.civica.newhires.documents.infrastructure.adapters.input.controllers;

import com.civica.newhires.documents.domain.ports.output.FileResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class FileDownloadController {

    private final FileResourceRepository fileResourceRepository;

    /**
     * Endpoint centralizado para descargar cualquier recurso de archivo del sistema.
     * * @param fileId El UUID del recurso en la tabla file_resources
     * @return El archivo con su nombre original y tipo MIME correcto
     */
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable UUID fileId) {
        log.info("Solicitando descarga de archivo con ID: {}", fileId);

        // 1. Recuperar metadatos de la base de datos
        var fileResource = fileResourceRepository.findById(fileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Archivo no encontrado"));

        try {
            // 2. Localizar el archivo físico en el disco
            Path filePath = Paths.get(fileResource.getPath());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                log.error("El archivo físico no existe o no se puede leer en la ruta: {}", fileResource.getPath());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El archivo físico ha sido movido o eliminado");
            }

            // 3. Preparar la respuesta con el nombre original y el tipo de contenido
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileResource.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getOriginalName() + "\"")
                    .body(resource);

        } catch (IOException e) {
            log.error("Error al intentar acceder al archivo: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al procesar el archivo");
        }
    }
}