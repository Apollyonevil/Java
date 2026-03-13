package com.example.SPStore.user.application.service;

import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.in.UserFindByIdInputPort;
import com.example.SPStore.user.domain.ports.out.IUserPersistencePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserFindByIdService implements UserFindByIdInputPort {

    private final IUserPersistencePort userPersistencePort;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserFindByIdService(IUserPersistencePort userPersistencePort, BCryptPasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userPersistencePort.findById(id);
    }

}