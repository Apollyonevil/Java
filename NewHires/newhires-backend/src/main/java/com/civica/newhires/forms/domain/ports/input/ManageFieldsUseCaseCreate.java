package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FieldDefinition;

public interface ManageFieldsUseCaseCreate {
    FieldDefinition createField(FieldDefinition field);

}