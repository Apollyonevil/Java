package com.example.SPStore.user.application.service;

import com.example.SPStore.user.application.dto.UserDTO;
import com.example.SPStore.user.application.mapper.DTOConverter;
import com.example.SPStore.user.domain.ports.in.UserFindAllInputPort;
import com.example.SPStore.user.domain.ports.out.IUserPersistencePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserFindAllService implements UserFindAllInputPort {

    private final IUserPersistencePort userPersistencePort;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserFindAllService(IUserPersistencePort userPersistencePort, BCryptPasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<UserDTO> findAll() {
        return userPersistencePort.findAll().stream()
                .map(DTOConverter::toUserDTO)
                .collect(Collectors.toList());
    }
}