package com.example.SPStore.order.application.mapper;

import com.example.SPStore.order.application.dto.*;
import com.example.SPStore.order.domain.model.*;


public class DTOConverter {

    public static OrderResponseDTO toOrderResponseDTO(Order order) {
        if (order == null) return null;
        
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setNum(order.getNum());
        dto.setDateCreation(order.getDateCreation());
        dto.setTotal(order.getTotal());
        
        if (order.getUser() != null) {
            dto.setUserName(order.getUser().getName());
        }
        
        
        return dto;
    }
}