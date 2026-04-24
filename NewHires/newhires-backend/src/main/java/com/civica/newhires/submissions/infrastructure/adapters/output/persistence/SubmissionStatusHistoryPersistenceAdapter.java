package com.civica.newhires.submissions.infrastructure.adapters.output.persistence;

import com.civica.newhires.submissions.domain.model.SubmissionStatusHistory;
import com.civica.newhires.submissions.domain.ports.output.SubmissionStatusHistoryPort;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionEntity;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.entities.SubmissionStatusHistoryEntity;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository.JpaSubmissionRepository;
import com.civica.newhires.submissions.infrastructure.adapters.output.persistence.repository.JpaSubmissionStatusHistoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubmissionStatusHistoryPersistenceAdapter implements SubmissionStatusHistoryPort {

    private final JpaSubmissionStatusHistoryRepository historyRepo;
    private final JpaSubmissionRepository submissionRepo;

    @Override
    public SubmissionStatusHistory save(SubmissionStatusHistory history) {
        SubmissionEntity submission = submissionRepo.findById(history.getSubmissionId())
                .orElseThrow(() -> new RuntimeException("Submission no encontrada: " + history.getSubmissionId()));

        SubmissionStatusHistoryEntity entity = new SubmissionStatusHistoryEntity();
        entity.setId(history.getId());
        entity.setSubmission(submission);
        entity.setStatus(history.getStatus());
        entity.setChangedAt(history.getChangedAt());
        entity.setChangedBy(history.getChangedBy());

        SubmissionStatusHistoryEntity saved = historyRepo.save(entity);


        return new SubmissionStatusHistory(
            saved.getId(),
            saved.getSubmission().getId(),
            saved.getStatus(),
            saved.getChangedAt(),
            saved.getChangedBy()
        );
    }

    @Override
    public List<SubmissionStatusHistory> findAllBySubmissionId(UUID submissionId) {
        return historyRepo.findBySubmissionId(submissionId).stream()
                .map(entity -> new SubmissionStatusHistory(
                    entity.getId(),
                    entity.getSubmission().getId(),
                    entity.getStatus(),
                    entity.getChangedAt(),
                    entity.getChangedBy()
                ))
                .toList();
    }
}