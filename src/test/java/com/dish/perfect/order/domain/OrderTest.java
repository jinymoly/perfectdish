package com.dish.perfect.order.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderTest {
    
    @Test
    @DisplayName("toString 확인")
    void testToString(){
        Order order = Order.builder()
                            .tableNo(3)
                            .menuName("시간 파스타")
                            .price(99000)
                            .count(2)
                            .totalPrice(convertToBigDecimal(getTotalPrice(99000, 2)))
                            .status(OrderStatus.RECEIVED)
                            .build();

        String savedText = order.toString();

        log.info("{}", savedText.toString());
        String expected = "3번 테이블 : 시간 파스타, 99000원, 2, 총 금액 217800원, [RECEIVED]";

        assertEquals(savedText, expected);
    }

    private int getTotalPrice(Integer price, int count){
        int intValue = price.intValue();
        int total = intValue * count;
        double vat = total * 0.1;
        return (int) Math.round(total + vat);
    }

    private BigDecimal convertToBigDecimal(int totalPrice){
        return new BigDecimal(totalPrice);
    }
}
