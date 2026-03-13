package com.example.SPStore.user.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import com.example.SPStore.user.infrastructure.output.persistence.entities.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    
    Optional<UserEntity> findByEmail(String email);
}