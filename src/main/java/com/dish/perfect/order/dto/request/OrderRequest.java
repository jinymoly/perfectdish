package com.dish.perfect.order.dto.request;

import java.math.BigDecimal;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequest {
    
    private final int tableNo;

    private final Menu menu;
    private final int count;
    private final Integer price;
    
    private final BigDecimal totalPrice;
    private final OrderStatus status;

    private final boolean isDiscount;

    @Builder
    private OrderRequest(int tableNo, Menu menu, int count, Integer price, BigDecimal totalPrice, OrderStatus status, boolean isDiscount){
        this.tableNo = tableNo;
        this.menu = menu;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.status = status;
        this.isDiscount = isDiscount;
    }
}
