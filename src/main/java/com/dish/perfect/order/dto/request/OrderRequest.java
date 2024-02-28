package com.dish.perfect.order.dto.request;

import java.util.List;

import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequest {
    
    private final int tableNo;

    private final List<OrderItem> orderItems;


    @Builder
    private OrderRequest(int tableNo, List<OrderItem> orderItems){
        this.tableNo = tableNo;
        this.orderItems = orderItems;
    }
}
