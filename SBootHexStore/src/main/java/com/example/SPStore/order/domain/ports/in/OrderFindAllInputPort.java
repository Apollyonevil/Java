package com.example.SPStore.order.domain.ports.in;

import com.example.SPStore.order.application.dto.OrderResponseDTO;
import java.util.List;

public interface OrderFindAllInputPort {
    List<OrderResponseDTO> findAll();

}