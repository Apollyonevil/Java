package com.example.SPStore.user.infrastructure.output.persistence;

import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.out.IUserPersistencePort;
import com.example.SPStore.user.infrastructure.output.persistence.mappers.UserMapper;
import com.example.SPStore.user.infrastructure.output.persistence.repository.IUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserPersistenceAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    public UserPersistenceAdapter(IUserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        return userMapper.toDomain(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }
}