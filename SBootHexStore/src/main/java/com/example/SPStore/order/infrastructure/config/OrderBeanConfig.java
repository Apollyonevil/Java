package com.example.SPStore.order.infrastructure.config;

import com.example.SPStore.order.application.service.OrderFindAllService;
import com.example.SPStore.order.application.service.OrderFindByIdService;
import com.example.SPStore.order.application.service.OrderFindByUserService;
import com.example.SPStore.order.application.service.OrderRegistrationService;
import com.example.SPStore.order.domain.ports.in.OrderFindAllInputPort;
import com.example.SPStore.order.domain.ports.in.OrderFindByIdInputPort;
import com.example.SPStore.order.domain.ports.in.OrderFindByUserInputPort;
import com.example.SPStore.order.domain.ports.in.OrderRegistrationInputPort;
import com.example.SPStore.order.domain.ports.out.IOrderPersistencePort;
import com.example.SPStore.orderdetail.domain.ports.out.IOrderDetailsPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderBeanConfig {

    @Bean
    public OrderFindAllInputPort orderFindAllInputPort(IOrderPersistencePort persistencePort) {
        return new OrderFindAllService(persistencePort);
    }

    @Bean
    public OrderFindByIdInputPort orderFindByIdInputPort(IOrderPersistencePort persistencePort) {
        return new OrderFindByIdService(persistencePort);
    }

    @Bean
    public OrderFindByUserInputPort orderFindByUserInputPort(IOrderPersistencePort persistencePort) {
        return new OrderFindByUserService(persistencePort);
    }

    @Bean
    public OrderRegistrationInputPort orderRegistrationInputPort(IOrderPersistencePort persistencePort) {
        return new OrderRegistrationService(persistencePort);
    }
}
