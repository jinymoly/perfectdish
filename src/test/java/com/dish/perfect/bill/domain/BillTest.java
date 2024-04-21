package com.dish.perfect.bill.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.bill.BillFixture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BillTest {

    private BillFixture billFixture = new BillFixture();

    @Test
    @DisplayName("출력 확인")
    void testToString(){
        Bill bill = Bill.builder()
                        .tableNo("2")
                        .orders(billFixture.ordersT2())
                        .createdAt(LocalDateTime.now())
                        .build();
        
        String savedBill = bill.toString();
        log.info("{}", savedBill.toString() );
    }
    
}
