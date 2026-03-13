package com.example.SPStore.product.infrastructure.config;

import com.example.SPStore.product.application.service.*;
import com.example.SPStore.product.domain.ports.in.*;
import com.example.SPStore.product.domain.ports.out.IProductPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductBeanConfig {

    @Bean
    public ProductSaveInputPort productSaveInputPort(IProductPersistencePort persistencePort) {
        return new ProductSaveService(persistencePort);
    }

    @Bean
    public ProductFindAllInputPort productFindAllInputPort(IProductPersistencePort persistencePort) {
        return new ProductFindAllService(persistencePort);
    }

    @Bean
    public ProductFindByIdInputPort productFindByIdInputPort(IProductPersistencePort persistencePort) {
        return new ProductFindByIdService(persistencePort);
    }

    @Bean
    public ProductUpdateInputPort productUpdateInputPort(IProductPersistencePort persistencePort) {
        return new ProductUpdateService(persistencePort);
    }

    @Bean
    public ProductDeleteInputPort productDeleteInputPort(IProductPersistencePort persistencePort) {
        return new ProductDeleteService(persistencePort);
    }
}