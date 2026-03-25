package com.example.SPStore.user.domain.ports.in;

import com.example.SPStore.user.domain.model.User;
import java.util.Optional;

public interface UserFindByEmailInputPort {

    Optional<User> findByEmail(String email); 
    
}