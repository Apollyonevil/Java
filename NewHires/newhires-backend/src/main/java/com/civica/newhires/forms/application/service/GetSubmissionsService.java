    package com.civica.newhires.forms.application.service;

    import com.civica.newhires.forms.domain.model.Submission;
    import com.civica.newhires.forms.domain.ports.input.GetSubmissionsUseCase;
    import com.civica.newhires.forms.domain.ports.output.FormRepository;
    import lombok.RequiredArgsConstructor;
    import java.util.List;

    @RequiredArgsConstructor
    public class GetSubmissionsService implements GetSubmissionsUseCase {
        private final FormRepository formRepository;

        @Override
        public List<Submission> execute() {
            return formRepository.findAllSubmissions();
        }
    }