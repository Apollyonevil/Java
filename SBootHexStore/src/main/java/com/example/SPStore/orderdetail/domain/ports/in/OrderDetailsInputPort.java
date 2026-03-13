package com.example.SPStore.orderdetail.domain.ports.in;

import java.util.List;

import com.example.SPStore.orderdetail.application.dto.OrderDetailDTO;
import com.example.SPStore.orderdetail.domain.model.OrderDetails;

public interface OrderDetailsInputPort {
    OrderDetails save(OrderDetails detailsOrder);
    List<OrderDetailDTO> findByOrderId(Integer orderId);
}