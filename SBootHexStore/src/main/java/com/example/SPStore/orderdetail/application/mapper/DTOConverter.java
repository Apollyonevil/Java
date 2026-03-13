package com.example.SPStore.orderdetail.application.mapper;

import com.example.SPStore.order.application.dto.*;
import com.example.SPStore.order.domain.model.*;
import com.example.SPStore.orderdetail.application.dto.*;
import com.example.SPStore.orderdetail.domain.model.*;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Convertidor de Objetos de Dominio a DTOs específicos para el módulo de Pedidos (Order).
 */
public class DTOConverter {

    public static OrderDetailDTO toOrderDetailDTO(OrderDetails detail) {
        if (detail == null) return null;
        
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(detail.getId());
        dto.setName(detail.getName());
        dto.setAmount(detail.getAmount());
        dto.setPrice(detail.getPrice());
        dto.setTotal(detail.getTotal());
        
        // Mapeamos datos básicos del producto para que la vista los muestre
        // sin necesidad de cargar toda la entidad Product.
        if (detail.getProduct() != null) {
            dto.setProductId(detail.getProduct().getId());
            dto.setProductName(detail.getProduct().getName());
        }
        return dto;
    }


}