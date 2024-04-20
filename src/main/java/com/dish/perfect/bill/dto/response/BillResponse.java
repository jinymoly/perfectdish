package com.dish.perfect.bill.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final List<Map<String, Integer>> orders;
    private final BigDecimal totalPrice;
    private final BillStatus orderStatus;

    public static BillResponse fromBillResponse(final Bill order){
        List<Map<String, Integer>> ordersWithCount = new ArrayList<>();
        
        for(Order menu : order.getOrders()){
        Map<String, Integer> ordersInfo = new HashMap<>();
             String menuName = menu.getMenu().getMenuName();
             int count = menu.getCount();
             ordersInfo.put(menuName, count);
             ordersWithCount.add(ordersInfo);
        }
        return BillResponse.builder()
                            .tableNo(order.getTableNo())
                            .orders(ordersWithCount)
                            .totalPrice(order.getTotalPrice())
                            .orderStatus(order.getOrderStatus())
                            .build();
    }
}
