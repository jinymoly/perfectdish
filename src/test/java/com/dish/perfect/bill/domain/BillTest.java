package com.dish.perfect.bill.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.bill.BillFixture;
import com.dish.perfect.order.OrderFixture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BillTest {

    private BillFixture billFixture = new BillFixture();
    private OrderFixture orderFixture = new OrderFixture();

    @Test
    @DisplayName("출력 확인")
    void testToString(){
        Bill bill = Bill.builder()
                        .tableNo("2")
                        .orders(orderFixture.ordersForBillT2())
                        .build();
        
        String savedBill = bill.toString();
        log.info("{}", savedBill.toString());
    }
}
