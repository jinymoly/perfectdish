package com.dish.perfect.bill.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.bill.BillFixture;
import com.dish.perfect.bill.domain.Bill;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BillCoreServiceTest {

    @Autowired
    private BillCoreService billCoreService;
    
    @Autowired
    private BillPresentationService billPresentationService;

    private BillFixture billFixture = new BillFixture();

    @Test
    @DisplayName("최종 주문서 생성 전 테이블 번호 확인 ")
    void createBillWithSameTableNo(){
        Bill bill1 = billFixture.orderRequest1.toBill();
        Bill bill2 = billFixture.orderRequest2.toBill();

        log.info("1/{}", bill1.getTableNo());
        log.info("2/{}", bill2.getTableNo());

        assertTrue(bill1.getTableNo().equals("2"));
        assertTrue(bill2.getTableNo().equals("3"));

        assertThat(bill1.getTableNo().equals(bill2.getTableNo()));
        
        assertNotEquals(bill1.getTableNo(), bill2.getTableNo());
    }

}
