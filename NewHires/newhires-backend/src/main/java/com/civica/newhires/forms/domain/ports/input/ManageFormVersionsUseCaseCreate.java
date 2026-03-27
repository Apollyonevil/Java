package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FormVersion;

public interface ManageFormVersionsUseCaseCreate {
    FormVersion createVersion(String createdBy, String description);

}