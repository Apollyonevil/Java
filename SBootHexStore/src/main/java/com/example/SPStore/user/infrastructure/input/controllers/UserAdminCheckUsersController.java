package com.example.SPStore.user.infrastructure.input.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SPStore.user.domain.ports.in.UserFindAllInputPort;
import com.example.SPStore.user.application.dto.UserDTO;

@RestController
@RequestMapping("/api/admin")
public class UserAdminCheckUsersController {

    @Autowired
    private UserFindAllInputPort userFindAllInputPort;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> adminUsers() {
        return ResponseEntity.ok(userFindAllInputPort.findAll());
    }
}