package com.example.SPStore.shared.infrastructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringBootSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Permitimos explícitamente la raíz y recursos para evitar el redireccionamiento inmediato
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/vendor/**").permitAll()
                .requestMatchers("/user/login", "/user/register", "/user/save", "/user/access").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/products/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
            .loginPage("/user/login")
            // Esta es la URL interna donde Spring escucha el POST del formulario
            .loginProcessingUrl("/user/login") 
            // Esta es la URL a la que Spring te manda DESPUÉS de validar (es un GET)
            .defaultSuccessUrl("/user/access", true) 
            .permitAll()
)
            .logout(logout -> logout
                .logoutUrl("/user/close")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration obj) throws Exception {
        return obj.getAuthenticationManager();
    }
}