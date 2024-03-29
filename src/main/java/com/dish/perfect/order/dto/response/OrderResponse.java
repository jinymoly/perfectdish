package com.dish.perfect.order.dto.response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OrderResponse {
    
    private final String tableNo;
    private final Map<String, Integer> orders;
    private final BigDecimal totalPrice;
    private final OrderStatus orderStatus;

    public static OrderResponse toResponse(final Order order){
        Map<String, Integer> ordersInfo = new HashMap<>();
        for(OrderItem menu : order.getOrderItems()){
             String menuName = menu.getMenu().getMenuName();
             int count = menu.getCount();
             ordersInfo.put(menuName, count);
        }
        return OrderResponse.builder()
                            .tableNo(order.getTableNo())
                            .orders(ordersInfo)
                            .totalPrice(order.getTotalPrice())
                            .orderStatus(order.getStatus())
                            .build();
    }
}
