package com.civica.newhires.documents.application.services;

import com.civica.newhires.documents.domain.model.FileResource;
import com.civica.newhires.documents.domain.ports.input.UploadFileUseCase;
import com.civica.newhires.documents.domain.ports.output.FileResourceRepository;
import com.civica.newhires.documents.domain.ports.output.FileStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Slf4j   
@Service
@RequiredArgsConstructor
public class UploadFileService implements UploadFileUseCase {

    private final FileResourceRepository fileResourceRepository;
    private final FileStoragePort fileStoragePort;

@Override
@Transactional
public FileResource execute(String originalName, String mimeType, Long size, byte[] content) {
    try {
        log.info("[UPLOAD] Iniciando subida: nombre={}, tipo={}, tamaño={}", originalName, mimeType, size);
        
        UUID fileId = UUID.randomUUID();
        String fileNameOnDisk = fileId + "_" + originalName;

        log.info("[UPLOAD] Guardando físicamente como: {}", fileNameOnDisk);
        String savedPath = fileStoragePort.save(content, fileNameOnDisk);
        log.info("[UPLOAD] Archivo guardado en ruta: {}", savedPath);

        FileResource fileResource = new FileResource(fileId, originalName, mimeType, size, savedPath);

        log.info("[UPLOAD] Persistiendo en file_resources con ID: {}", fileId);
        FileResource saved = fileResourceRepository.save(fileResource);
        log.info("[UPLOAD] Persistido correctamente: {}", saved.getId());

        return saved;

    } catch (IOException e) {
        log.error("[UPLOAD] IOException al guardar físicamente: {}", e.getMessage(), e);
        throw new RuntimeException("Error al procesar el archivo físico", e);
    } catch (Exception e) {
        log.error("[UPLOAD] Error inesperado en UploadFileService: {}", e.getMessage(), e);
        throw e;
    }
}
}