package com.example.SPStore.order.infrastructure.input.controllers;

import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.in.UserFindByIdInputPort;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderSummaryController {

    @Autowired
    private UserFindByIdInputPort userFindByIdInputPort; 

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getOrderSummary(HttpSession session) {
        Object idUser = session.getAttribute("iduser");
        if (idUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
        }

        var details = session.getAttribute("cart");
        var order = session.getAttribute("order");

        if (details == null) {
            return ResponseEntity.badRequest().build(); // 400 si no hay carrito
        }

        User user = userFindByIdInputPort.findById(Integer.parseInt(idUser.toString()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

      
        Map<String, Object> response = new HashMap<>();
        response.put("cart", details);
        response.put("order", order);
        response.put("user", user);
        
        return ResponseEntity.ok(response); 
    }
}