package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import java.util.List;

public interface GetFormStructureUseCase {
    List<FieldDefinition> execute();
}