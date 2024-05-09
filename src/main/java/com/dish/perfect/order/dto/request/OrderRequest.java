package com.dish.perfect.order.dto.request;

import com.dish.perfect.order.domain.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequest {

    private final String menuName;
    private final int quantity;

    private final String tableNo;

    private final OrderStatus status;

    @Builder
    private OrderRequest(String menuName, int quantity, String tableNo, OrderStatus status) {
        this.menuName = menuName;
        this.quantity = quantity;
        this.tableNo = tableNo;
        this.status = status;
    }

}
