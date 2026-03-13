package com.example.SPStore.user.application.mapper;

import com.example.SPStore.user.application.dto.*;
import com.example.SPStore.product.application.dto.*;
import com.example.SPStore.orderdetail.domain.model.*;
import com.example.SPStore.orderdetail.application.dto.*;
import com.example.SPStore.user.domain.model.*;
import com.example.SPStore.product.domain.model.*;
import com.example.SPStore.order.domain.model.*;
import com.example.SPStore.order.application.dto.*;

public class DTOConverter {

    // 1. De Dominio Producto a DTO
    public static ProductDTO toProductDTO(Product product) {
        if (product == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage());
        dto.setAmount(product.getAmount());
        return dto;
    }

    // 2. De Dominio Usuario a DTO
    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        dto.setType(user.getType());
        return dto;
    }

    // 3. De Dominio Detalle a DTO
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

    // 4. De Dominio Orden a DTO (LIMPIO de detalles)
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

    // 5. De ProductDTO a Dominio
    public static Product toProduct(ProductDTO dto) {
        if (dto == null) return null;
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImage(dto.getImage());
        product.setAmount(dto.getAmount());
        return product;
    }

    // 6. De UserDTO a Dominio
    public static User toUser(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user.setType(dto.getType());
        return user;
    }
}