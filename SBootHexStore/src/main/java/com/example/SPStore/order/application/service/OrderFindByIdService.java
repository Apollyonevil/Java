package com.example.SPStore.order.application.service;

import com.example.SPStore.order.application.dto.OrderResponseDTO;
import com.example.SPStore.order.application.mapper.DTOConverter;
import com.example.SPStore.order.domain.ports.in.OrderFindByIdInputPort;
import com.example.SPStore.order.domain.ports.out.IOrderPersistencePort;
import java.util.Optional;

public class OrderFindByIdService implements OrderFindByIdInputPort {
    private final IOrderPersistencePort orderPersistencePort;

    public OrderFindByIdService(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public Optional<OrderResponseDTO> findById(Integer id) {
        return orderPersistencePort.findById(id)
                .map(DTOConverter::toOrderResponseDTO);
    }
}