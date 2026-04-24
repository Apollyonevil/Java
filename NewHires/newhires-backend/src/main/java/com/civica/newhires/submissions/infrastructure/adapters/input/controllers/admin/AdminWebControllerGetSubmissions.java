package com.civica.newhires.submissions.infrastructure.adapters.input.controllers.admin;

import com.civica.newhires.employee.infrastructure.persistence.repository.EmployeeUserRepository;
import com.civica.newhires.submissions.application.dto.SubmissionResponseDTO;
import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.input.GetSubmissionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/forms")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminWebControllerGetSubmissions {

    private final GetSubmissionsUseCase getSubmissionsUseCase;
    private final EmployeeUserRepository employeeRepository;

    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponseDTO>> getAll() {
        List<Submission> submissions = getSubmissionsUseCase.execute();

        List<SubmissionResponseDTO> result = submissions.stream().map(sub -> {
            String employeeName = employeeRepository.findById(sub.getEmployeeId().toString())
                    .map(e -> e.getUsername())
                    .orElse("Sistema");

            return new SubmissionResponseDTO(
                sub.getId(),
                sub.getCandidateName(),
                sub.getEmail(),
                sub.getEmployeeId(),
                employeeName,
                sub.getCreatedAt(),
                sub.getSubmittedAt(),
                sub.getStatus(),
                sub.getToken()  
            );
        }).toList();

        return ResponseEntity.ok(result);
    }
}