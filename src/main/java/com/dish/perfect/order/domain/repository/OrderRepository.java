package com.dish.perfect.order.domain.repository;

import java.util.List;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.dto.request.OrderRequest;

public interface OrderRepository {

    Order createOrder(OrderRequest orderDto);

    List<Order> getAllOrders();
    
    List<Order> getOrders(int tableNo);
    List<Order> getOrderByStatus(OrderStatus status);
    
}
