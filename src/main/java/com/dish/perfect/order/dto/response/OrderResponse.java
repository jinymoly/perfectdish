package com.dish.perfect.order.dto.response;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OrderResponse {
    
    private final String tableNo;
    private final String menuName; 
    private final int count;
    private final OrderStatus orderStatus;

    public static OrderResponse fromOrderResponse(final Order order){
        return OrderResponse.builder()
                            .tableNo(order.getTableNo())
                            .menuName(order.getOrderInfo().getMenu().getMenuName())
                            .count(order.getOrderInfo().getQuantity())
                            .orderStatus(order.getOrderStatus())
                            .build();
    }
}
