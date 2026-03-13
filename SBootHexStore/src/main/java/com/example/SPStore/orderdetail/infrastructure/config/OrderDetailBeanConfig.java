package com.example.SPStore.orderdetail.infrastructure.config;

import com.example.SPStore.orderdetail.application.service.OrderDetailsService;
import com.example.SPStore.orderdetail.domain.ports.in.OrderDetailsInputPort;
import com.example.SPStore.orderdetail.domain.ports.out.IOrderDetailsPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderDetailBeanConfig {


    @Bean
    public OrderDetailsInputPort orderDetailsInputPort(
            IOrderDetailsPersistencePort orderDetailsPersistencePort) {
        // Registra el servicio de detalles de pedido
        return new OrderDetailsService(orderDetailsPersistencePort);
    }
}