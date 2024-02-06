package com.dish.perfect.order.domain.repository;

import java.util.List;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.dto.request.OrderRequest;

public interface OrderRepository {

    Order createOrder(OrderRequest orderDto);
    List<Order> getOrders(int tableNo);
    List<Order> getOrderByStatus(OrderStatus status);
    List<Order> addOrderByTableNo(int tableNo, OrderRequest orderDto);

    // 할인메뉴인지 ? 
    // 할인 메뉴라면 5프로 할인 정책 
    
}
