package com.civica.newhires.auth.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.civica.newhires.auth.infrastructure.persistence.repository.AdminUserRepository;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {
private final AdminUserRepository adminUserRepository;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return adminUserRepository.findByUsername(username)
            .map(user -> {
                boolean matches = new BCryptPasswordEncoder().matches("admin123", user.getPassword());
                return User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles("ADMIN")
                        .build();
            })
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
}

}