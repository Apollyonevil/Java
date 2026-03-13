package com.example.SPStore.order.infrastructure.output.persistence;

import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.order.domain.ports.out.IOrderPersistencePort;
import com.example.SPStore.orderdetail.infrastructure.output.persistence.entities.OrderDetailsEntity;
import com.example.SPStore.order.infrastructure.output.persistence.entities.OrderEntity;
import com.example.SPStore.order.infrastructure.output.persistence.mappers.OrderMapper;
import com.example.SPStore.user.infrastructure.output.persistence.mappers.UserMapper;
import com.example.SPStore.order.infrastructure.output.persistence.repository.IOrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public OrderPersistenceAdapter(IOrderRepository orderRepository, OrderMapper orderMapper, UserMapper userMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderMapper.toEntity(order);

        if (orderEntity.getDetail() != null) {
            System.out.println("DEBUG: Se han recibido " + orderEntity.getDetail().size() + " detalles para guardar.");
            for (OrderDetailsEntity dt : orderEntity.getDetail()) {
                dt.setOrder(orderEntity); 
                // Verificamos si el producto está llegando al detalle
                if (dt.getProduct() != null) {
                    System.out.println("DEBUG: Detalle con Producto ID: " + dt.getProduct().getId());
                } else {
                    System.out.println("DEBUG: ¡OJO! Un detalle no tiene producto asignado.");
                }
            }
        } else {
            System.out.println("DEBUG: ¡ALERTA! La lista de detalles (getDetail()) es NULL.");
        }

        // Usamos saveAndFlush para forzar a Hibernate a sincronizar con la DB inmediatamente
        OrderEntity savedOrder = orderRepository.saveAndFlush(orderEntity);
        System.out.println("DEBUG: Orden guardada con ID: " + savedOrder.getId());
        
        return orderMapper.toDomain(savedOrder);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id).map(orderMapper::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUser(User user) {
        // Usamos el mapeo de usuario para la consulta
        return orderRepository.findByUser(userMapper.toEntity(user)).stream()
                .map(orderMapper::toDomain)
                .collect(Collectors.toList());
    }
}