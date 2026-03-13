package com.example.SPStore.order.domain.ports.in;

import com.example.SPStore.order.application.dto.OrderResponseDTO;
import com.example.SPStore.user.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface OrderFindByIdInputPort {

    Optional<OrderResponseDTO> findById(Integer id);
}