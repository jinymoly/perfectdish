package com.dish.perfect.order.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderUpdateRequest {
    
    private final String tableNo;
    
    private final String menuName;
    private final int quantity;

    @Builder
    private OrderUpdateRequest(String menuName, int quantity, String tableNo){
        this.menuName = menuName;
        this.quantity = quantity;
        this.tableNo = tableNo;
    }
    
}
