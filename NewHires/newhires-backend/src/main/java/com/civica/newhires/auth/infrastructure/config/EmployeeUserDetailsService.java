package com.civica.newhires.auth.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.civica.newhires.auth.infrastructure.persistence.repository.EmployeeUserRepository;
import com.civica.newhires.auth.infrastructure.persistence.entities.EmployeeUserEntity; 

@Service
@RequiredArgsConstructor
public class EmployeeUserDetailsService implements UserDetailsService {
    
    private final EmployeeUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().name()) // El rol viene del Enum de la entidad
                        .disabled(!user.isEnabled())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}