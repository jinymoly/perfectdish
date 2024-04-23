package com.dish.perfect.bill.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.order.domain.Order;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BillRequest {

    private final String tableNo;

    private final List<Order> orders;
    private final BillStatus status;

    @Builder
    private BillRequest(String tableNo, List<Order> orders, BillStatus status) {
        this.tableNo = tableNo;
        this.orders = orders;
        this.status = BillStatus.NOTSERVED;
    }

    public Bill toBill() {
        List<Order> ordersFromRequest = new ArrayList<>(orders); 

        return Bill.builder()
                .tableNo(tableNo)
                .orders(ordersFromRequest)
                .orderStatus(status)
                .build();
    }
}
