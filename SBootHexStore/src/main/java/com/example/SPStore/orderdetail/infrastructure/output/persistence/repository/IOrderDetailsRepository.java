package com.example.SPStore.orderdetail.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Importante añadir esta importación

import com.example.SPStore.orderdetail.infrastructure.output.persistence.entities.OrderDetailsEntity;

@Repository
public interface IOrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Integer> {

    List<OrderDetailsEntity> findByOrderId(Integer orderId);
    
}