package com.dish.perfect.orderItem.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Menu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderItemTest {

    private MenuFixture menu = new MenuFixture();
    
    @Test
    @DisplayName("orderItem 생성")
    void createOrderItemMap() {
        Menu menuFixtureForOrderItem = menu.fixRequestA().toEntity();

        OrderItem newOrderItem = OrderItem.builder()
                .id(1L)
                //.order(new Order("3"))
                .tableNo("2")
                .orderItemStatus(OrderItemStatus.CREATED)
                .menu(menuFixtureForOrderItem)
                .count(3)
                .createdAt(LocalDateTime.now())
                .build();

        String orderItemInfo = newOrderItem.toString();
        log.info("{}", orderItemInfo);
    }
}
