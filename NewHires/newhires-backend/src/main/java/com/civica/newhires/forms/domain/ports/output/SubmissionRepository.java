package com.civica.newhires.forms.domain.ports.output;

import com.civica.newhires.forms.domain.model.Submission;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubmissionRepository {
    
    void save(Submission submission);

    Optional<Submission> findByEmployeeId(UUID employeeId);

    List<Submission> findAll();
    Optional<Submission> findById(UUID id);
}