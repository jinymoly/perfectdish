package com.dish.perfect.order.dto.request;

import com.dish.perfect.order.domain.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@Builder
public class OrderRequest {

    private String menuName;
    private int quantity;
    private String tableNo;
    private String phoneNumber;
    private OrderStatus status;

}
