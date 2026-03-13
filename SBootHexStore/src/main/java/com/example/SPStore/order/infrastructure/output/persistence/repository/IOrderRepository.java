package com.example.SPStore.order.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// IMPORTANTE: Importamos las ENTITIES, no los modelos de dominio
import com.example.SPStore.order.infrastructure.output.persistence.entities.OrderEntity;
import com.example.SPStore.user.infrastructure.output.persistence.entities.UserEntity;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Integer> {
    
    // El método ahora busca por la entidad de usuario
    List<OrderEntity> findByUser(UserEntity user);
}