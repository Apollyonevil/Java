package com.example.SPStore.user.domain.ports.in;

import com.example.SPStore.user.domain.model.User;

public interface UserSaveInputPort {
    User save(User user);

}