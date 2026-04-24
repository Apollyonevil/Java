package com.civica.newhires.employee.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.civica.newhires.employee.application.dto.EmployeeDTO;
import com.civica.newhires.employee.application.dto.EmployeeRequest;
import com.civica.newhires.employee.domain.model.Employee;
import com.civica.newhires.employee.domain.model.UserRole;
import com.civica.newhires.employee.domain.ports.output.EmployeePort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeUserService {

    private final EmployeePort repository;
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

        Employee employee = new Employee(
            UUID.randomUUID().toString(),
            request.username(),
            passwordEncoder.encode(request.password()),
            true,
            request.role() != null ? UserRole.valueOf(request.role()) : UserRole.EMPLOYEE
        );

        return mapToDTO(repository.save(employee));
    }

    @Transactional
    public EmployeeDTO update(String id, EmployeeRequest request) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Employee updated = new Employee(
            existing.getId(),
            request.username(),
            request.password() != null && !request.password().isBlank()
                ? passwordEncoder.encode(request.password())
                : existing.getPassword(),
            existing.isEnabled(),
            request.role() != null ? UserRole.valueOf(request.role()) : existing.getRole()
        );

        return mapToDTO(repository.save(updated));
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return new EmployeeDTO(
            employee.getId(),
            employee.getUsername(),
            employee.isEnabled(),
            employee.getRole().name()
        );
    }
}