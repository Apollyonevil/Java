package com.example.SPStore.user.domain.ports.in;

import com.example.SPStore.user.application.dto.UserDTO;
import java.util.List;

public interface UserFindAllInputPort {

    List<UserDTO> findAll(); 
}