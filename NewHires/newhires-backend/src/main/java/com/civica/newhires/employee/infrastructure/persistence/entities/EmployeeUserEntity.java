package com.civica.newhires.employee.infrastructure.persistence.entities;

import com.civica.newhires.employee.domain.model.UserRole;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter @Setter
public class EmployeeUserEntity {

    @Id
    private String id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role; 
}