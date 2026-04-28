package com.civica.newhires.employee.domain.model;

import java.util.UUID;

public class Employee {
    private final String id;
    private final String username;
    private String password;
    private final boolean enabled;
    private final UserRole role;


    public Employee(String id, String username, String password, boolean enabled, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }


    public static Employee create(String username, String hashedPassword, String role) {
        return new Employee(
            UUID.randomUUID().toString(),
            username,
            hashedPassword,
            true,
            role != null ? UserRole.valueOf(role) : UserRole.EMPLOYEE
        );
    }


    public Employee updateWith(String username, String hashedPassword, String role) {
        return new Employee(
            this.id,
            username,
            hashedPassword != null ? hashedPassword : this.password,
            this.enabled,
            role != null ? UserRole.valueOf(role) : this.role
        );
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isEnabled() { return enabled; }
    public UserRole getRole() { return role; }
}