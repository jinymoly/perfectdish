package com.dish.perfect.order.domain.repository;

import java.util.List;
import java.util.Map;

import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.orderItem.domain.OrderItem;

public interface OrderRepository {

    Map<Integer, List<OrderItem>> saveOrderMap(OrderRequest orderRequest);

    Map<Integer, List<OrderItem>> getOrderByTableNo(int tableNo);

    Map<Integer, List<OrderItem>> getAllOrders();

    void clear();

}
