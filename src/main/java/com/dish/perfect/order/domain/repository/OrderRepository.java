package com.dish.perfect.order.domain.repository;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.dto.request.OrderRequest;

public interface OrderRepository {

    // 주문 생성
    // 주문 ㅇ내역 조회
    Order createOrder(OrderRequest orderDto);

    Order getOrder(Long id);

    Order getOrderByStatus(OrderStatus status);

    Long getNextId();


}
