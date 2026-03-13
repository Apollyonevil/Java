package com.example.SPStore.user.infrastructure.input.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.in.UserFindByIdInputPort;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserAccessController {

    @Autowired
    private UserFindByIdInputPort userFindByIdInputPort;

    @GetMapping("/access")
    public ResponseEntity<Map<String, Object>> access(HttpSession session) {
        Object iduser = session.getAttribute("iduser");
        if (iduser == null) return ResponseEntity.status(401).build();

        User user = userFindByIdInputPort.findById(Integer.parseInt(iduser.toString()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        String role = (user.getType() != null && user.getType().equalsIgnoreCase("ADMIN")) ? "ADMIN" : "USER";
        
        return ResponseEntity.ok(Map.of("role", role));
    }
}