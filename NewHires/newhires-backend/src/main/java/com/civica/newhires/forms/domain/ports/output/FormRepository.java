package com.civica.newhires.forms.domain.ports.output;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue;
import com.civica.newhires.forms.domain.model.Submission;
import java.util.List;
import java.util.UUID;

public interface FormRepository {

    List<FieldDefinition> findAllFieldDefinitions(); 
    FieldDefinition findDefinitionById(UUID id);
    FieldDefinition saveDefinition(FieldDefinition definition);
    void deleteDefinition(UUID id);
    
    void saveValues(List<FieldValue> values);
    void saveSubmission(Submission submission);
    List<Submission> findAllSubmissions();
}