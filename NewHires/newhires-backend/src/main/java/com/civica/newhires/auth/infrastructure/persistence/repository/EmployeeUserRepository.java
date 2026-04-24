package com.civica.newhires.auth.infrastructure.persistence.repository;

import com.civica.newhires.auth.infrastructure.persistence.entities.EmployeeUserEntity; // IMPORT CLAVE 1
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeUserRepository extends JpaRepository<EmployeeUserEntity, String> {
    Optional<EmployeeUserEntity> findByUsername(String username);
}