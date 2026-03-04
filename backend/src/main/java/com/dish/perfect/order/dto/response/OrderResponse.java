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
    
    private final Long id;
    private final Long billId;
    private final String tableNo;
    private final String phoneNumber;
    private final String menuName; 
    private final int quantity;
    private final int price;
    private final int totalPrice;
    private final OrderStatus orderStatus;
    private final java.time.LocalDateTime createdAt;

    public static OrderResponse fromOrderResponse(final Order order){
        int price = order.getOrderInfo().getMenu().getPrice();
        int quantity = order.getOrderInfo().getQuantity();
        return OrderResponse.builder()
                            .id(order.getId())
                            .billId(order.getBill() != null ? order.getBill().getId() : null)
                            .tableNo(order.getTableNo())
                            .phoneNumber(order.getPhoneNumber())
                            .menuName(order.getOrderInfo().getMenu().getMenuName())
                            .quantity(quantity)
                            .price(price)
                            .totalPrice(price * quantity)
                            .orderStatus(order.getOrderStatus())
                            .createdAt(order.getCreatedAt())
                            .build();
    }
}
