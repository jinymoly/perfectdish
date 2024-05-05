package com.dish.perfect.bill.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.menu.domain.Menu;
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
    private final BillStatus billStatus;

    public static BillResponse fromBillResponse(final Bill bill){
        List<Map<String, Integer>> ordersWithCount = new ArrayList<>();
        
        for(Order order : bill.getOrders()){
        Map<String, Integer> ordersInfo = new HashMap<>();
             Menu menu = order.getOrderInfo().getMenu();
             String menuName = menu.getMenuName();
             int count = order.getOrderInfo().getQuantity();
             ordersInfo.put(menuName, count);
             ordersWithCount.add(ordersInfo);
        }
        return BillResponse.builder()
                            .tableNo(bill.getTableNo())
                            .orders(ordersWithCount)
                            .totalPrice(bill.getTotalPrice())
                            .billStatus(bill.getBillStatus())
                            .build();
    }
}
