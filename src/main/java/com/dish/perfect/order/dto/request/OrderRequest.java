package com.dish.perfect.order.dto.request;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequest {
    
    private final int tableNo;

    private final Menu menu;
    private final int count;
    
    private final OrderStatus status;


    @Builder
    private OrderRequest(int tableNo, Menu menu, int count, OrderStatus status){
        this.tableNo = tableNo;
        this.menu = menu;
        this.count = count;
        this.status = status;
    }
}
