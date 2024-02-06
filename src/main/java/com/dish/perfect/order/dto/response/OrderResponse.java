package com.dish.perfect.order.dto.response;

import java.math.BigDecimal;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OrderResponse {
    
    private final int tableNo;
    private final String menuName;
    private final Integer price;
    private final int count;
    private final BigDecimal totalPrice;
    private final OrderStatus orderStatus;

    public static OrderResponse toResponse(final Order order){
        return OrderResponse.builder()
                            .tableNo(order.getTableNo())
                            .menuName(order.getMenuName())
                            .price(order.getPrice())
                            .count(order.getCount())
                            .totalPrice(order.getTotalPrice())
                            .orderStatus(order.getStatus())
                            .build();
    }
}
