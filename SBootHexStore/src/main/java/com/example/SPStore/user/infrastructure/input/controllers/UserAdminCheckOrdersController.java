package com.example.SPStore.user.infrastructure.input.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SPStore.order.domain.ports.in.OrderFindAllInputPort;
import com.example.SPStore.order.application.dto.OrderResponseDTO;

@RestController
@RequestMapping("/api/admin")
public class UserAdminCheckOrdersController {

    @Autowired
    private OrderFindAllInputPort orderFindAllInputPort;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> adminOrders() {
        return ResponseEntity.ok(orderFindAllInputPort.findAll());
    }
}