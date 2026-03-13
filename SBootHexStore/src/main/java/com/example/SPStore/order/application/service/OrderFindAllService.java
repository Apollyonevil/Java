package com.example.SPStore.order.application.service;

import com.example.SPStore.order.application.dto.OrderResponseDTO;
import com.example.SPStore.order.application.mapper.DTOConverter;
import com.example.SPStore.order.domain.ports.in.OrderFindAllInputPort;
import com.example.SPStore.order.domain.ports.out.IOrderPersistencePort;
import java.util.List;

public class OrderFindAllService implements OrderFindAllInputPort {
    private final IOrderPersistencePort orderPersistencePort;

    public OrderFindAllService(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public List<OrderResponseDTO> findAll() {
        return orderPersistencePort.findAll().stream()
                .map(DTOConverter::toOrderResponseDTO)
                .toList();
    }
}