package com.example.SPStore.user.domain.ports.in;

import com.example.SPStore.user.application.dto.UserDTO;
import com.example.SPStore.user.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserFindAllInputPort {

    List<UserDTO> findAll(); 
}