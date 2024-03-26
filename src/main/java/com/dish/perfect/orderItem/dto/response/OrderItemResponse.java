package com.dish.perfect.orderItem.dto.response;

import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OrderItemResponse {
    
    private final String tableNo;
    private final String menuName; 
    private final int count;
    private final OrderItemStatus orderStatus;

    public static OrderItemResponse fromOrderItemResponse(final OrderItem order){
        return OrderItemResponse.builder()
                            .tableNo(order.getTableNo())
                            .menuName(order.getMenu().getMenuName())
                            .count(order.getCount())
                            .orderStatus(order.getOrderItemStatus())
                            .build();
    }
}
