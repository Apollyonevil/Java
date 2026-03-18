package com.civica.newhires.auth.application.mapper;

import com.civica.newhires.auth.application.dto.InvitationRequest;
import com.civica.newhires.auth.domain.model.Invitation;

public class InvitationMapper {

    public static Invitation toDomain(InvitationRequest request) {
        return new Invitation(request.email());
    }
    
}