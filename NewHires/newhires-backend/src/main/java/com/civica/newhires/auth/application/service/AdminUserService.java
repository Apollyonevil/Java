package com.civica.newhires.auth.application.service;

import com.civica.newhires.auth.application.dto.AdminUserDTO;
import com.civica.newhires.auth.application.dto.AdminUserRequest;
import com.civica.newhires.auth.infrastructure.persistence.entities.AdminUserEntity;
import com.civica.newhires.auth.infrastructure.persistence.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<AdminUserDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AdminUserDTO create(AdminUserRequest request) {
        if (repository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        AdminUserEntity user = new AdminUserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(true);
        
        return mapToDTO(repository.save(user));
    }

    @Transactional
    public AdminUserDTO update(String id, AdminUserRequest request) {
        AdminUserEntity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setUsername(request.username());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        
        return mapToDTO(repository.save(user));
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

    private AdminUserDTO mapToDTO(AdminUserEntity entity) {
        return new AdminUserDTO(entity.getId(), entity.getUsername(), entity.isEnabled());
    }
}