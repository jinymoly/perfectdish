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
    
    private final Long id;
    private final String tableNo;
    private final List<Map<String, Object>> orders;
    private final BigDecimal totalPrice;
    private final BillStatus billStatus;
    private final java.time.LocalDateTime createdAt;

    public static BillResponse fromBillResponse(final Bill bill){
        List<Map<String, Object>> ordersWithCount = new ArrayList<>();
        
        for(Order order : bill.getOrders()){
             Map<String, Object> ordersInfo = new HashMap<>();
             Menu menu = order.getOrderInfo().getMenu();
             ordersInfo.put("menuName", menu.getMenuName());
             ordersInfo.put("quantity", order.getOrderInfo().getQuantity());
             ordersInfo.put("price", menu.getPrice());
             ordersWithCount.add(ordersInfo);
        }
        return BillResponse.builder()
                            .id(bill.getId())
                            .tableNo(bill.getTableNo())
                            .orders(ordersWithCount)
                            .totalPrice(bill.getTotalPrice())
                            .billStatus(bill.getBillStatus())
                            .createdAt(bill.getCreatedAt())
                            .build();
    }
}
