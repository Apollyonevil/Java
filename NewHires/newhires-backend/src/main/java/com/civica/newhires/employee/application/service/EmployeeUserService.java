package com.civica.newhires.employee.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.civica.newhires.employee.application.dto.EmployeeDTO;
import com.civica.newhires.employee.application.dto.EmployeeRequest;
import com.civica.newhires.employee.application.mapper.EmployeeMapper;
import com.civica.newhires.employee.domain.exception.EmployeeAlreadyExistsException;
import com.civica.newhires.employee.domain.exception.EmployeeNotFoundException;
import com.civica.newhires.employee.domain.model.Employee;
import com.civica.newhires.employee.domain.ports.output.EmployeePort;
import com.civica.newhires.employee.domain.ports.output.PasswordHasher;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeUserService {

    private final EmployeePort repository;
    private final PasswordHasher passwordHasher;  // tu nuevo puerto

    public List<EmployeeDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDTO create(EmployeeRequest request) {
        if (repository.findByUsername(request.username()).isPresent()) {
            throw new EmployeeAlreadyExistsException(request.username());
        }

        Employee employee = Employee.create(
            request.username(),
            passwordHasher.hash(request.password()),
            request.role()
        );

        return EmployeeMapper.toDTO(repository.save(employee));
    }

    @Transactional
    public EmployeeDTO update(String id, EmployeeRequest request) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        String hashedPassword = request.password() != null && !request.password().isBlank()
                ? passwordHasher.hash(request.password())
                : null;

        return EmployeeMapper.toDTO(repository.save(existing.updateWith(
            request.username(),
            hashedPassword,
            request.role()
        )));
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}