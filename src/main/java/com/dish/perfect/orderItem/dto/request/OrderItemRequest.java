package com.dish.perfect.orderItem.dto.request;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemRequest {

    private final Menu menu;
    private final int count;
    
    private final OrderItemStatus status;


    @Builder
    private OrderItemRequest(Menu menu, int count, OrderItemStatus status){
        this.menu = menu;
        this.count = count;
        this.status = status;
    }

    public OrderItem toEntity(){
        return OrderItem.builder()
                        .menu(menu)
                        .count(count)
                        .itemstatus(OrderItemStatus.CREATED)
                        .build();
    }
}
