package com.civica.newhires.fields.domain.ports.input;

import com.civica.newhires.fields.domain.model.FieldDefinition;
import java.util.UUID;

public interface ManageFieldsUseCaseUpdate {
    FieldDefinition updateField(UUID id, FieldDefinition field);
    FieldDefinition toggleField(UUID id);

}