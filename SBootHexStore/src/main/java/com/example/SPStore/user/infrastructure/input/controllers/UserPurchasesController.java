package com.example.SPStore.user.infrastructure.input.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.in.UserFindByIdInputPort;
import com.example.SPStore.order.domain.ports.in.OrderFindByUserInputPort;
import com.example.SPStore.order.application.dto.OrderResponseDTO;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserPurchasesController {

    @Autowired
    private OrderFindByUserInputPort orderFindByUserInputPort;

    @Autowired
    private UserFindByIdInputPort userFindByIdInputPort;

    @GetMapping("/purchases")
    public ResponseEntity<List<OrderResponseDTO>> checkPurchases(HttpSession session) {
        Object idUser = session.getAttribute("iduser");
        if (idUser == null) return ResponseEntity.status(401).build();

        User user = userFindByIdInputPort.findById(Integer.parseInt(idUser.toString()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return ResponseEntity.ok(orderFindByUserInputPort.findByUser(user));
    }
}