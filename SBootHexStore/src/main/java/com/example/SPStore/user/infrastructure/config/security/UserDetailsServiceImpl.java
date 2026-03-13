package com.example.SPStore.user.infrastructure.config.security;

import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.in.UserFindByEmailInputPort;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserFindByEmailInputPort userFindByEmailInputPort; // Usamos el puerto, no el repositorio directamente

    @Autowired
    HttpSession session;

    private Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userFindByEmailInputPort.findByEmail(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Guardamos el ID en sesión para que los controladores lo usen (como tenías antes)
            session.setAttribute("iduser", user.getId());
            
            return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())

            .roles(user.getType()) 
            .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}