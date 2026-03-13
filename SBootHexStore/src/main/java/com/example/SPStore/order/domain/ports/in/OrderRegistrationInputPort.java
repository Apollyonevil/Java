package com.example.SPStore.order.domain.ports.in;

import com.example.SPStore.order.application.dto.OrderResponseDTO;
import com.example.SPStore.order.domain.model.Order;

public interface OrderRegistrationInputPort {
    OrderResponseDTO save(Order order);
    String orderNumberGenerate();
}