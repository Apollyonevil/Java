package com.civica.newhires.auth.domain.ports.input;

import com.civica.newhires.auth.domain.model.Invitation; 

public interface ManageInvitationsUseCase {
    Invitation createInvitation(String email);
}