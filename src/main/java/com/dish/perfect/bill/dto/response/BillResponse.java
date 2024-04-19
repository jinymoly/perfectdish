package com.dish.perfect.bill.dto.response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.order.domain.Order;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class BillResponse {
    
    private final String tableNo;
    private final Map<String, Integer> orders;
    private final BigDecimal totalPrice;
    private final BillStatus orderStatus;

    public static BillResponse fromOrderResponse(final Bill order){
        Map<String, Integer> ordersInfo = new HashMap<>();
        for(Order menu : order.getOrders()){
             String menuName = menu.getMenu().getMenuName();
             int count = menu.getCount();
             ordersInfo.put(menuName, count);
        }
        return BillResponse.builder()
                            .tableNo(order.getTableNo())
                            .orders(ordersInfo)
                            .totalPrice(order.getTotalPrice())
                            .orderStatus(order.getOrderStatus())
                            .build();
    }
}
