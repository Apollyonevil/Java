package com.civica.newhires.employee.infrastructure.persistence;

import com.civica.newhires.employee.domain.model.Employee;
import com.civica.newhires.employee.domain.model.UserRole;
import com.civica.newhires.employee.domain.ports.output.EmployeePort;
import com.civica.newhires.employee.infrastructure.persistence.entities.EmployeeUserEntity;
import com.civica.newhires.employee.infrastructure.persistence.repository.EmployeeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeePersistenceAdapter implements EmployeePort {

    private final EmployeeUserRepository jpaRepository;

    @Override
    public List<Employee> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Employee> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Employee> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(this::toDomain);
    }

    @Override
    public Employee save(Employee employee) {
        return toDomain(jpaRepository.save(toEntity(employee)));
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }

    private Employee toDomain(EmployeeUserEntity entity) {
        return new Employee(
            entity.getId(),
            entity.getUsername(),
            entity.getPassword(),
            entity.isEnabled(),
            entity.getRole()
        );
    }

    private EmployeeUserEntity toEntity(Employee domain) {
        EmployeeUserEntity entity = new EmployeeUserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());
        entity.setEnabled(domain.isEnabled());
        entity.setRole(domain.getRole());
        return entity;
    }
}