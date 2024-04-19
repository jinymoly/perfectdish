package com.dish.perfect.bill.dto.request;

import java.util.List;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.order.domain.Order;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BillRequest {

    // TO-DO
    // member 따로 Point Entity로 관리할건지
    // Order에서 Member정보가 무조건 필요하지 않음 >> 회원/비회원

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
        return Bill.builder()
                .tableNo(tableNo)
                .orders(orders)
                .orderStatus(status)
                .build();
    }
}
