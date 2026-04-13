package com.civica.newhires.employee.domain.model;

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

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isEnabled() { return enabled; }
    public UserRole getRole() { return role; }
}