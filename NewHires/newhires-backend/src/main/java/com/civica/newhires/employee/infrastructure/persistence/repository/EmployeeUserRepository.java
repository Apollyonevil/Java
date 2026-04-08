package com.civica.newhires.employee.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.civica.newhires.employee.infrastructure.persistence.entities.EmployeeUserEntity;

import java.util.Optional;

@Repository
public interface EmployeeUserRepository extends JpaRepository<EmployeeUserEntity, String> {
    Optional<EmployeeUserEntity> findByUsername(String username);
}