package com.example.SPStore.user.infrastructure.input.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SPStore.orderdetail.domain.ports.in.OrderDetailsInputPort;
import com.example.SPStore.orderdetail.application.dto.OrderDetailDTO;

@RestController
@RequestMapping("/api/admin")
public class UserAdminCheckOrderDetailsController {

    @Autowired
    private OrderDetailsInputPort orderDetailInputPort;

    @GetMapping("/checkdetails/{id}")
    public ResponseEntity<List<OrderDetailDTO>> adminCheckDetails(@PathVariable Integer id) {
        List<OrderDetailDTO> details = orderDetailInputPort.findByOrderId(id);
        return ResponseEntity.ok(details);
    }
}