package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.GetSubmissionsUseCase;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
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