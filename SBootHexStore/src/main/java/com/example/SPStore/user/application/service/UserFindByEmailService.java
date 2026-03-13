package com.example.SPStore.user.application.service;

import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.in.UserFindByEmailInputPort;
import com.example.SPStore.user.domain.ports.out.IUserPersistencePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserFindByEmailService implements UserFindByEmailInputPort {

    private final IUserPersistencePort userPersistencePort;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserFindByEmailService(IUserPersistencePort userPersistencePort, BCryptPasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userPersistencePort.findByEmail(email);
    }

}