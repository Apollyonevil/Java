package com.example.SPStore.user.domain.ports.out;

import com.example.SPStore.user.domain.model.User;
import java.util.Optional;
import java.util.List;

public interface IUserPersistencePort {
    User save(User user);
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}