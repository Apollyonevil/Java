package com.civica.newhires.forms.infrastructure.adapters.input.web;

import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.ports.input.GetSubmissionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
public class AdminFormController {
    private final GetSubmissionsUseCase getSubmissionsUseCase;

    @GetMapping("/submissions")
    public List<Submission> getAll() {
        return getSubmissionsUseCase.execute();
    }
}