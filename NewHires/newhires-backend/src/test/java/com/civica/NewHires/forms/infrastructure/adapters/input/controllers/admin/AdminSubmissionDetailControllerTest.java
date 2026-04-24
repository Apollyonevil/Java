package com.civica.newhires.forms.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldValueRepository;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin.AdminSubmissionDetailController;
import com.civica.newhires.forms.domain.model.FieldType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminSubmissionDetailController.class)
class AdminSubmissionDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubmissionRepository submissionRepository;

    @MockitoBean
    private JpaFieldValueRepository fieldValueRepository;

    @MockitoBean
    private JpaFieldDefinitionRepository fieldDefinitionRepository;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaObtenerDetalleDeSubmission() throws Exception {
        UUID submissionId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        UUID fieldId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();

        Submission submission = new Submission(candidateId, employeeId, "token123");


        FieldValueEntity fieldValue = new FieldValueEntity();
        fieldValue.setId(UUID.randomUUID());
        fieldValue.setEmployeeId(employeeId);
        fieldValue.setFieldDefinitionId(fieldId);
        fieldValue.setValue("García López");

        FieldDefinitionEntity fieldDef = new FieldDefinitionEntity();
        fieldDef.setId(fieldId);
        fieldDef.setLabel("Apellidos");
        fieldDef.setType(FieldType.TEXT);

        when(submissionRepository.findById(submissionId)).thenReturn(Optional.of(submission));
        when(fieldValueRepository.findByEmployeeId(employeeId)).thenReturn(List.of(fieldValue));
        when(fieldDefinitionRepository.findById(fieldId)).thenReturn(Optional.of(fieldDef));

        mockMvc.perform(get("/api/admin/forms/submissions/{id}/detail", submissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.candidateName").value("Juan García"))
                .andExpect(jsonPath("$.email").value("juan@test.com"))
                .andExpect(jsonPath("$.fields[0].label").value("Apellidos"))
                .andExpect(jsonPath("$.fields[0].value").value("García López"))
                .andExpect(jsonPath("$.fields[0].isFile").value(false));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaRetornar500SiNoEncuentraSubmission() throws Exception {
        UUID id = UUID.randomUUID();
        when(submissionRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/admin/forms/submissions/{id}/detail", id))
                .andExpect(status().isBadRequest()); // <-- cambia esto
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deberiaMarcarArchivosPDFComoIsFile() throws Exception {
        UUID submissionId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        UUID fieldId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();
        
        Submission submission = new Submission(candidateId, employeeId, "token123");

        FieldValueEntity fieldValue = new FieldValueEntity();
        fieldValue.setId(UUID.randomUUID());
        fieldValue.setEmployeeId(employeeId);
        fieldValue.setFieldDefinitionId(fieldId);
        fieldValue.setValue("DNI Garcia Lopez Juan.pdf");

        FieldDefinitionEntity fieldDef = new FieldDefinitionEntity();
        fieldDef.setId(fieldId);
        fieldDef.setLabel("DNI");
        fieldDef.setType(FieldType.PDF);

        when(submissionRepository.findById(submissionId)).thenReturn(Optional.of(submission));
        when(fieldValueRepository.findByEmployeeId(employeeId)).thenReturn(List.of(fieldValue));
        when(fieldDefinitionRepository.findById(fieldId)).thenReturn(Optional.of(fieldDef));

        mockMvc.perform(get("/api/admin/forms/submissions/{id}/detail", submissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fields[0].isFile").value(true));
    }
}