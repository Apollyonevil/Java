package com.example.SPStore.orderdetail.infrastructure.output.persistence;

import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import com.example.SPStore.orderdetail.domain.ports.out.IOrderDetailsPersistencePort;
import com.example.SPStore.orderdetail.infrastructure.output.persistence.mappers.OrderDetailMapper;
import com.example.SPStore.orderdetail.infrastructure.output.persistence.repository.IOrderDetailsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDetailsPersistenceAdapter implements IOrderDetailsPersistencePort {

    private final IOrderDetailsRepository repository;
    private final OrderDetailMapper mapper;

    public OrderDetailsPersistenceAdapter(IOrderDetailsRepository repository, OrderDetailMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public OrderDetails save(OrderDetails orderDetail) {
        return mapper.toDomain(repository.save(mapper.toEntity(orderDetail)));
    }

    @Override
    public List<OrderDetails> findByOrderId(Integer orderId) {
        return repository.findByOrderId(orderId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}