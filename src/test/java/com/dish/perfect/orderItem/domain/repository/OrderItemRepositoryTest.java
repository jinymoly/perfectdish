package com.dish.perfect.orderItem.domain.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.orderItem.OrderItemFixture;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

@SpringBootTest
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private MenuRepository menuRepository;

    private OrderItemFixture fixtureO = new OrderItemFixture();
    private MenuFixture fixtureM = new MenuFixture();

    @AfterEach
    void clear() {
        orderItemRepository.deleteAllInBatch();
    }

    @BeforeEach
    void createMenu() {
        menuRepository.save(fixtureM.fixRequestA().toEntity());
        menuRepository.save(fixtureM.fixRequestB().toEntity());
        menuRepository.save(fixtureM.fixRequestC().toEntity());
        menuRepository.save(fixtureM.fixRequestD().toEntity());
        menuRepository.save(fixtureM.fixRequestE().toEntity());
        menuRepository.save(fixtureM.fixRequestF().toEntity());
        menuRepository.save(fixtureM.fixRequestG().toEntity());
        menuRepository.save(fixtureM.fixRequestH().toEntity());
        menuRepository.save(fixtureM.fixRequestI().toEntity());
    }

    @Test
    @DisplayName("주문 생성시 status.CREATED")
    void createOrder() {
        OrderItem orderB = orderItemRepository.save(fixtureO.orderItemRequestB.toEntity());
        assertTrue(orderB.getOrderItemStatus().equals(OrderItemStatus.CREATED));

    }

    

}
