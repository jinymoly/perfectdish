package com.dish.perfect.order.dto.request;

import java.util.List;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequest {

    // TO-DO
    // member 따로 Point Entity로 관리할건지
    // Order에서 Member정보가 무조건 필요하지 않음 >> 회원/비회원

    private final String tableNo;

    private final List<OrderItem> orderItems;

    @Builder
    private OrderRequest(String tableNo, List<OrderItem> orderItems) {
        this.tableNo = tableNo;
        this.orderItems = orderItems;
    }

    public Order toEntity() {
        return Order.builder()
                .tableNo(tableNo)
                .orderItems(orderItems)
                .status(OrderStatus.NOTSERVED)
                .build();
    }
}
