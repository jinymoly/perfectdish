package com.dish.perfect.order.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchOrderRequest {
    private String tableNo;
    private String phoneNumber;
    private List<OrderItemRequest> items;
}
