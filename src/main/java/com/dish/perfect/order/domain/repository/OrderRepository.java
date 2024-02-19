package com.dish.perfect.order.domain.repository;

import java.util.Map;

import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.orderItem.domain.OrderItem;

public interface OrderRepository {

    Map<Integer, OrderItem> saveOrderMap(OrderRequest orderRequest);

    Map<Integer, OrderItem> getOrderByTableNo(int tableNo);

    Map<Integer, OrderItem> getAllOrders();

    void clear();

}
