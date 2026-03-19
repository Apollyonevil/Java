package com.civica.newhires.forms.infrastructure.adapters.output.persistence;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubmissionPersistenceAdapter implements SubmissionRepository {

    private final JpaSubmissionRepository submissionRepo;
    private final FormPersistenceMapper mapper; 

    @Override
    public void save(Submission submission) {
        submissionRepo.save(mapper.toEntity(submission));
    }

    @Override
    public Optional<Submission> findById(UUID id) {
        return submissionRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Submission> findByEmployeeId(UUID employeeId) {
        return submissionRepo.findByEmployeeId(employeeId).map(mapper::toDomain);
    }

    @Override
    public List<Submission> findAll() {
        return submissionRepo.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

        @Override
    public void deleteById(UUID id) {
        submissionRepo.deleteById(id);
    }

    @Override
    public Optional<Submission> findByToken(String token) {
        return submissionRepo.findByToken(token).map(mapper::toDomain);
    }
}