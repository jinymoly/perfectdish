package com.dish.perfect.order.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.order.OrderFixture;

public class OrderTest {

    private OrderFixture orderFixture = new OrderFixture();

    @Test
    @DisplayName("toString 확인")
    void testToString(){
        Order order = Order.builder()
        .tableNo(2)
        .orderList(orderFixture.orderItemsFixture2())
        .finalPrice(BigDecimal.ZERO)
        .status(OrderStatus.NOTSERVED)
        .build();
        
        String savedText = order.toString();

        String expected = "[그라탕, 15000원, 2개, total: 30000원, [CREATED], D: true, 크림 소스 파스타, 18000원, 2개, total: 36000원, [CREATED], D: true] /최종 합계: 0원 [NOTSERVED]";

        assertEquals(savedText, expected);
    }

}
