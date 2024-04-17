package com.dish.perfect.order.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Menu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderTest {

    private MenuFixture menu = new MenuFixture();
    
    @Test
    @DisplayName("order 생성")
    void createOrderMap() {
        Menu menuFixtureForOrder = menu.fixRequestA().toEntity();

        Order newOrder = Order.builder()
                .id(1L)
                .tableNo("2")
                .orderStatus(OrderStatus.CREATED)
                .menu(menuFixtureForOrder)
                .count(3)
                .createdAt(LocalDateTime.now())
                .build();

        String orderInfo = newOrder.toString();
        log.info("{}", orderInfo);
    }
}
