package com.civica.newhires.shared.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.civica.newhires.shared.infrastructure.persistence.entities.AdminUserEntity;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUserEntity, String> {
    Optional<AdminUserEntity> findByUsername(String username);
}