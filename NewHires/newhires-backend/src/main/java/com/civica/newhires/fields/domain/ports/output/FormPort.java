package com.civica.newhires.fields.domain.ports.output;

import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.model.FieldValue;
import java.util.List;
import java.util.UUID;

public interface FormPort {
    List<FieldDefinition> findAllFieldDefinitions(); 
    FieldDefinition findDefinitionById(UUID id);
    FieldDefinition saveDefinition(FieldDefinition definition);
    void deleteDefinition(UUID id);
    void saveValues(List<FieldValue> values);
}