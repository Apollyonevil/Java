package com.example.SPStore.order.application.service;

import com.example.SPStore.order.application.dto.OrderResponseDTO;
import com.example.SPStore.order.application.mapper.DTOConverter;
import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.order.domain.ports.in.OrderRegistrationInputPort;
import com.example.SPStore.order.domain.ports.out.IOrderPersistencePort;
import java.time.LocalDateTime;

public class OrderRegistrationService implements OrderRegistrationInputPort {

    private final IOrderPersistencePort orderPersistencePort;

    public OrderRegistrationService(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public OrderResponseDTO save(Order order) {
        if (order.getDateCreation() == null) {
            order.setDateCreation(LocalDateTime.now());
        }
        if (order.getNum() == null || order.getNum().isEmpty()) {
            order.setNum(orderNumberGenerate());
        }

        Order savedOrder = orderPersistencePort.save(order);
        return DTOConverter.toOrderResponseDTO(savedOrder);
    }

    @Override
    public String orderNumberGenerate() {
        long count = orderPersistencePort.findAll().size();
        return String.format("%010d", count + 1);
    }
}