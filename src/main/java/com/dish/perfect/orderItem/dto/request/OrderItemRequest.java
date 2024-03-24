package com.dish.perfect.orderItem.dto.request;

import java.util.HashMap;
import java.util.Map;

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
        Map<Menu, Integer> newOrderItem = new HashMap<>();
        newOrderItem.put(new Menu(menu.getMenuName()), Integer.valueOf(count));
        return OrderItem.builder()
                        .orderItem(newOrderItem)
                        .itemstatus(OrderItemStatus.CREATED)
                        .build();
    }
}
