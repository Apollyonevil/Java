package com.example.SPStore.order.infrastructure.output.persistence.mappers;

import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.orderdetail.infrastructure.output.persistence.mappers.OrderDetailMapper;
import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import com.example.SPStore.user.domain.model.User; 
import com.example.SPStore.order.infrastructure.output.persistence.entities.OrderEntity;
import com.example.SPStore.user.infrastructure.output.persistence.entities.UserEntity; 

import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final OrderDetailMapper detailMapper;

    public OrderMapper(OrderDetailMapper detailMapper) {
        this.detailMapper = detailMapper;
    }

    public OrderEntity toEntity(Order domain) {
        if (domain == null) return null;
        
        OrderEntity entity = new OrderEntity();
        entity.setId(domain.getId());
        entity.setNum(domain.getNum());
        entity.setDateCreation(domain.getDateCreation());
        entity.setTotal(domain.getTotal());

        // Mapeo ligero del Usuario (solo ID para persistencia)
        if (domain.getUser() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(domain.getUser().getId()); 
            entity.setUser(userEntity);
        }

        // Mapeo de la lista de detalles
        if (domain.getDetails() != null) {
            entity.setDetail(domain.getDetails().stream()
                    .map(detailMapper::toEntity) 
                    .collect(Collectors.toList()));
        }

        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        if (entity == null) return null;
        
        Order domain = new Order();
        domain.setId(entity.getId());
        domain.setNum(entity.getNum());
        domain.setDateCreation(entity.getDateCreation());
        domain.setTotal(entity.getTotal());
        
        // RECONSTRUCCIÓN DEL USUARIO: Importante para que el dominio tenga el ID del cliente
        if (entity.getUser() != null) {
            User userDomain = new User();
            userDomain.setId(entity.getUser().getId());
            // Si tu UserEntity tiene nombre, podrías pasarlo aquí para el DTO final
            userDomain.setName(entity.getUser().getName()); 
            domain.setUser(userDomain);
        }

        // Mapeo de la lista de detalles de vuelta al dominio
        if (entity.getDetail() != null) {
            domain.setDetails(entity.getDetail().stream()
                .map(detailMapper::toDomain)
                .collect(Collectors.toList()));
        }
        
        return domain;
    }
}