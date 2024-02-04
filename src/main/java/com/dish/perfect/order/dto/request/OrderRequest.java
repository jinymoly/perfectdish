package com.dish.perfect.order.dto.request;

import java.math.BigDecimal;

import com.dish.perfect.order.domain.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequest {
    
    private final int tableNo;
    private final String menuName;
    private final int count;
    private final Integer price;
    
    private final BigDecimal totalPrice;
    private final OrderStatus status;

    @Builder
    private OrderRequest(int tableNo, String menuName, int count, Integer price, BigDecimal totalPrice, OrderStatus status){
        this.tableNo = tableNo;
        this.menuName = menuName;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
