package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import java.util.UUID;

public interface ManageFieldsUseCase {
    FieldDefinition createField(FieldDefinition field);
    FieldDefinition updateField(UUID id, FieldDefinition field);
    void deleteField(UUID id);
}