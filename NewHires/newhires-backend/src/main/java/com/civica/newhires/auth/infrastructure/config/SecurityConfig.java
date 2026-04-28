package com.civica.newhires.auth.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.civica.newhires.employee.infrastructure.config.EmployeeUserDetailsService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final EmployeeUserDetailsService employeeUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
                    StringBuilder hexString = new StringBuilder();
                    for (byte b : hash) {
                        hexString.append(String.format("%02x", b));
                    }
                    return hexString.toString();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException("SHA-256 not available", e);
                }
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .userDetailsService(employeeUserDetailsService)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers("/api/v1/forms/**").permitAll()
                .requestMatchers("/api/forms/structure").permitAll()
                .requestMatchers("/api/admin/users/**").hasRole("ADMIN")
                .requestMatchers("/api/submissions/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/admin/invite/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/admin/submissions/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/admin/fields/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/admin/versions/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/admin/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults()); 

        return http.build();
    }
}