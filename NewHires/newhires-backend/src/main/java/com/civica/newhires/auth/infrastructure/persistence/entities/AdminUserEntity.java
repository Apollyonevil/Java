package com.civica.newhires.auth.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin_users")
@Getter @Setter
public class AdminUserEntity {

    @Id
    private String id;
    private String username;
    private String password;
    private boolean enabled;
}