package com.civica.newhires.submissions.application.service;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSubmissionsService implements GetSubmissionsUseCase {
    
    private final SubmissionRepository submissionRepository;

    @Override
    public List<Submission> execute() {
        return submissionRepository.findAll();
    }
}