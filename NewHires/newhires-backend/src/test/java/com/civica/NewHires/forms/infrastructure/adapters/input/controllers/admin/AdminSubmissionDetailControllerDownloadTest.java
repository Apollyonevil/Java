package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin.AdminSubmissionDetailControllerDownload;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminSubmissionDetailControllerDownload.class)
@TestPropertySource(properties = "storage.location=./uploads-test")
class AdminSubmissionDetailControllerDownloadTest {

    @Autowired
    private MockMvc mockMvc;

    @TempDir
    Path tempDir;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaRetornar404SiArchivoNoExiste() throws Exception {
        mockMvc.perform(get("/api/admin/forms/files/archivo-inexistente.pdf"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaDescargarArchivoExistente() throws Exception {
        Path uploadsDir = Path.of("./uploads-test");
        Files.createDirectories(uploadsDir);
        File testFile = uploadsDir.resolve("test-archivo.pdf").toFile();
        Files.write(testFile.toPath(), "contenido del pdf".getBytes());

        mockMvc.perform(get("/api/admin/forms/files/test-archivo.pdf"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", 
                    "attachment; filename=\"test-archivo.pdf\""));

        testFile.delete();
    }
}