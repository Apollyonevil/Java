package com.civica.newhires.auth.application.service;

import com.civica.newhires.auth.application.dto.EmployeeDTO;
import com.civica.newhires.auth.application.dto.EmployeeRequest;
import com.civica.newhires.auth.domain.model.UserRole;
import com.civica.newhires.auth.infrastructure.persistence.entities.EmployeeUserEntity;
import com.civica.newhires.auth.infrastructure.persistence.repository.EmployeeUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeUserService {

    private final EmployeeUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<EmployeeDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDTO create(EmployeeRequest request) {
        if (repository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("El empleado ya existe");
        }

        EmployeeUserEntity user = new EmployeeUserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(true);
        user.setRole(request.role() != null ? UserRole.valueOf(request.role()) : UserRole.EMPLOYEE);
        
        return mapToDTO(repository.save(user));
    }

    @Transactional
    public EmployeeDTO update(String id, EmployeeRequest request) {
        EmployeeUserEntity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setUsername(request.username());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        
        if (request.role() != null) {
            user.setRole(UserRole.valueOf(request.role()));
        }
        
        return mapToDTO(repository.save(user));
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

    // He dejado solo una versión de este método y bien cerrada
    private EmployeeDTO mapToDTO(EmployeeUserEntity entity) {
        return new EmployeeDTO(
            entity.getId(), 
            entity.getUsername(), 
            entity.isEnabled(), 
            entity.getRole().name()
        );
    }
}