package com.example.SPStore.orderdetail.application.mapper;

import com.example.SPStore.orderdetail.application.dto.*;
import com.example.SPStore.orderdetail.domain.model.*;



public class DTOConverter {

    public static OrderDetailDTO toOrderDetailDTO(OrderDetails detail) {
        if (detail == null) return null;
        
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(detail.getId());
        dto.setName(detail.getName());
        dto.setAmount(detail.getAmount());
        dto.setPrice(detail.getPrice());
        dto.setTotal(detail.getTotal());

        if (detail.getProduct() != null) {
            dto.setProductId(detail.getProduct().getId());
            dto.setProductName(detail.getProduct().getName());
        }
        return dto;
    }


}