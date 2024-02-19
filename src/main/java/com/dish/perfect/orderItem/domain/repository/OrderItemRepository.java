package com.dish.perfect.orderItem.domain.repository;

import java.util.List;

import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;
import com.dish.perfect.orderItem.dto.request.OrderItemRequest;

public interface OrderItemRepository {

    OrderItem createOrder(OrderItemRequest orderDto);

    List<OrderItem> getAllOrders();
    
    List<OrderItem> getOrders(int tableNo);
    List<OrderItem> getOrderByStatus(OrderItemStatus status);
    
}
