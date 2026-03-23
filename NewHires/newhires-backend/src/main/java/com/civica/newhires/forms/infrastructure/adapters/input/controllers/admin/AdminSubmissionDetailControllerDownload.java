package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldValueRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
import com.civica.newhires.forms.domain.model.Submission;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminSubmissionDetailControllerDownload {

    private final SubmissionRepository submissionRepository;
    private final JpaFieldValueRepository fieldValueRepository;
    private final JpaFieldDefinitionRepository fieldDefinitionRepository;

    @Value("${storage.location}")
    private String storageLocation;

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(storageLocation).resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    public record SubmissionDetailResponse(
        String candidateName,
        String email,
        String status,
        List<FieldValueDTO> fields
    ) {}

    public record FieldValueDTO(
        String label,
        String value,
        boolean isFile,
        String type
    ) {}
}