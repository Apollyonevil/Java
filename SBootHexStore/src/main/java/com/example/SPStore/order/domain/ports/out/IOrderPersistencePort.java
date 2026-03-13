
package com.example.SPStore.order.domain.ports.out;

import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.user.domain.model.User; // Importante
import java.util.List;
import java.util.Optional;

public interface IOrderPersistencePort {
    Order save(Order order);
    Optional<Order> findById(Integer id);
    List<Order> findAll();
    // Cámbialo a esto para que coincida con tu adaptador:
    List<Order> findByUser(User user); 
}