package com.example.SPStore.order.application.service;

import com.example.SPStore.order.application.dto.OrderResponseDTO;
import com.example.SPStore.order.application.mapper.DTOConverter;
import com.example.SPStore.order.domain.ports.in.OrderFindByUserInputPort;
import com.example.SPStore.order.domain.ports.out.IOrderPersistencePort;
import com.example.SPStore.user.domain.model.User;
import java.util.List;

public class OrderFindByUserService implements OrderFindByUserInputPort {
    private final IOrderPersistencePort orderPersistencePort;

    public OrderFindByUserService(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public List<OrderResponseDTO> findByUser(User user) {
        return orderPersistencePort.findByUser(user).stream()
                .map(DTOConverter::toOrderResponseDTO)
                .toList();
    }
}