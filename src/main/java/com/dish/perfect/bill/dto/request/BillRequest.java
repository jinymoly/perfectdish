package com.dish.perfect.bill.dto.request;

import java.util.List;

import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.order.domain.Order;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BillRequest {

    private final String tableNo;
    private final List<Order> orders;
    private final BillStatus billStatus;

    @Builder
    private BillRequest(String tableNo, List<Order> orders, BillStatus billStatus) {
        this.tableNo = tableNo;
        this.orders = orders;
        this.billStatus = BillStatus.NOTSERVED;
    }
}
