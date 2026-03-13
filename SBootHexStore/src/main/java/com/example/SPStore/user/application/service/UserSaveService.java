package com.example.SPStore.user.application.service;

import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.in.UserSaveInputPort;
import com.example.SPStore.user.domain.ports.out.IUserPersistencePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserSaveService implements UserSaveInputPort {

    private final IUserPersistencePort userPersistencePort;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserSaveService(IUserPersistencePort userPersistencePort, BCryptPasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {

        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        if (user.getType() == null || user.getType().isEmpty()) {
            user.setType("USER");
        }
        
        return userPersistencePort.save(user);
    }

}