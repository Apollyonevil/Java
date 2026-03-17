package com.civica.newhires.forms.infrastructure.adapters.output.persistence;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldValueRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormPersistenceAdapter implements FormRepository {

    private final JpaFieldDefinitionRepository definitionRepo;
    private final JpaFieldValueRepository valueRepo;
    private final JpaSubmissionRepository submissionRepo;
    private final FormPersistenceMapper mapper;

    @Override
    public List<FieldDefinition> findAllFieldDefinitions() {
        return definitionRepo.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public FieldDefinition findDefinitionById(UUID id) {
        return definitionRepo.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Campo no encontrado"));
    }

    @Override
    public void saveValues(List<FieldValue> values) {
        var entities = values.stream().map(mapper::toEntity).toList();
        valueRepo.saveAll(entities);
    }

    @Override
    public void saveSubmission(Submission submission) {
        SubmissionEntity entity = new SubmissionEntity();
        entity.setId(submission.getId());
        entity.setEmployeeId(submission.getEmployeeId());
        entity.setSubmittedAt(submission.getSubmittedAt());
        entity.setStatus(submission.getStatus());
        submissionRepo.save(entity);
    }

    @Override
    public List<Submission> findAllSubmissions() {
        return submissionRepo.findAll().stream()
                .map(e -> new Submission(e.getId(), e.getEmployeeId(), e.getSubmittedAt(), e.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDefinition(UUID id) {
        definitionRepo.deleteById(id);
    }

    @Override
    public FieldDefinition saveDefinition(FieldDefinition definition) {
        var entity = mapper.toEntity(definition);
        var savedEntity = definitionRepo.save(entity);
        return mapper.toDomain(savedEntity);
    }
}