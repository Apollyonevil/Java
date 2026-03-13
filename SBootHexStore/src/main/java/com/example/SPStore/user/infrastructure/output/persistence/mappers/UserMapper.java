package com.example.SPStore.user.infrastructure.output.persistence.mappers;

import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.infrastructure.output.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserEntity toEntity(User user) {
        if (user == null) return null;
        return new UserEntity(
            user.getId(), user.getName(), user.getUsername(), 
            user.getEmail(), user.getAddress(), user.getPhone(), 
            user.getType(), user.getPassword()
        );
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        User user = new User(
            entity.getId(), entity.getName(), entity.getUsername(), 
            entity.getEmail(), entity.getAddress(), entity.getPhone(), 
            entity.getType(), entity.getPassword()
        );
        // NO tocamos la lista de órdenes aquí para no romper el login
        return user;
    }
}