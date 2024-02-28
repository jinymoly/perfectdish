package com.dish.perfect.orderItem.dto.request;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemRequest {
    
    private final int tableNo;

    private final Menu menu;
    private final int count;
    
    private final OrderItemStatus status;


    @Builder
    private OrderItemRequest(int tableNo, Menu menu, int count, OrderItemStatus status){
        this.tableNo = tableNo;
        this.menu = menu;
        this.count = count;
        this.status = status;
    }
}
