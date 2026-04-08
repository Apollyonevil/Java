package com.civica.newhires.submissions.domain.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.civica.newhires.submissions.domain.model.Submission;

public interface SubmissionRepository {
    
    Submission save(Submission submission); 
    
    Optional<Submission> findByEmployeeId(UUID employeeId);
    List<Submission> findAll();
    Optional<Submission> findById(UUID id);
    void deleteById(UUID id); 
    Optional<Submission> findByToken(String token);
}