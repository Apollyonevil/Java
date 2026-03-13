package com.example.SPStore.orderdetail.domain.ports.out;

import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import java.util.List;

public interface IOrderDetailsPersistencePort {
    
    OrderDetails save(OrderDetails orderDetail);

    List<OrderDetails> findByOrderId(Integer orderId);
}