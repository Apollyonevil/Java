package com.example.SPStore.user.infrastructure.input.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserCloseController {

    @PostMapping("/close")
    public ResponseEntity<Void> logOut(HttpSession session) {
        session.removeAttribute("iduser");
        session.invalidate(); 
        return ResponseEntity.ok().build();
    }
}