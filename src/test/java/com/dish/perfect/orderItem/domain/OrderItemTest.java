package com.dish.perfect.orderItem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderItemTest {

    @Test
    @DisplayName("toString 확인")
    void testToString(){
        OrderItem order = OrderItem.builder()
                            .tableNo(3)
                            .menuName("시간 파스타")
                            .price(99000)
                            .count(2)
                            .isDiscount(true)
                            .itemstatus(OrderItemStatus.CREATED)
                            .build();
        order.addTotalPrice();
        String savedText = order.toString();

        String expected = "시간 파스타, 94050원, 2개, total: 198000원, [CREATED], D: true";

        assertEquals(savedText, expected);
    }

    @Test
    void calculateTotal(){
        OrderItem order = OrderItem.builder().isDiscount(true).price(3000).count(2).build();

        BigDecimal totalPrice = order.addTotalPrice();

        assertEquals(totalPrice, new BigDecimal(5700));
    }
}
