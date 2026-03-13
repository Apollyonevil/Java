package com.example.SPStore.orderdetail.application.service;

import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import com.example.SPStore.orderdetail.domain.ports.in.OrderDetailsInputPort;
import com.example.SPStore.orderdetail.domain.ports.out.IOrderDetailsPersistencePort;
import com.example.SPStore.orderdetail.application.dto.OrderDetailDTO; // Importante
import com.example.SPStore.user.application.mapper.DTOConverter; // Importante


import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailsService implements OrderDetailsInputPort {

    private final IOrderDetailsPersistencePort detailsPersistencePort;

    public OrderDetailsService(IOrderDetailsPersistencePort detailsPersistencePort) {
        this.detailsPersistencePort = detailsPersistencePort;
    }

    @Override
    public OrderDetails save(OrderDetails detailsOrder) {
        return detailsPersistencePort.save(detailsOrder);
    }

    @Override
    public List<OrderDetailDTO> findByOrderId(Integer orderId) {

        List<OrderDetails> details = detailsPersistencePort.findByOrderId(orderId);

        return details.stream()
                .map(DTOConverter::toOrderDetailDTO)
                .collect(Collectors.toList());
    }
}