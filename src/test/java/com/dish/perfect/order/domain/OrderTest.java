package com.dish.perfect.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Menu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderTest {

    private MenuFixture menu = new MenuFixture();
    
    @Test
    @DisplayName("order 생성 toString 확인")
    void createOrderMap() {
        Menu menuFixtureForOrder = menu.fixRequestA().toMenuEntity();

        Order newOrder = Order.builder()
                .tableNo("2")
                .orderStatus(OrderStatus.CREATED)
                .orderInfo(OrderInfo.of(menuFixtureForOrder, 3))
                .build();

        String orderInfo = newOrder.toString();
        log.info("{}", orderInfo);
    }
}
