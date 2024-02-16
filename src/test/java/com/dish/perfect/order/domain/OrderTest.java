package com.dish.perfect.order.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    @DisplayName("toString 확인")
    void testToString(){
        Order order = Order.builder()
                            .tableNo(3)
                            .menuName("시간 파스타")
                            .price(99000)
                            .count(2)
                            .isDiscount(true)
                            .status(OrderStatus.CREATED)
                            .build();
        order.addTotalPrice();
        String savedText = order.toString();

        String expected = "tableNo.3 : 시간 파스타, 94050원, 2, 총 금액 198000원, [CREATED], D: true";

        assertEquals(savedText, expected);
    }

    @Test
    void calculateTotal(){
        Order order = Order.builder().isDiscount(true).price(3000).count(2).build();

        BigDecimal totalPrice = order.addTotalPrice();

        assertEquals(totalPrice, new BigDecimal(5700));
    }
}
