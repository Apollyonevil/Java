package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FormVersion;
import java.util.List;

public interface ManageFormVersionsUseCaseGetAll {
    List<FormVersion> getAllVersions();
}