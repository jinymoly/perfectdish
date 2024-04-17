package com.dish.perfect.order.domain.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.order.OrderFixture;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuRepository menuRepository;

    private OrderFixture fixtureO = new OrderFixture();
    private MenuFixture fixtureM = new MenuFixture();

    @AfterEach
    void clear() {
        orderRepository.deleteAllInBatch();
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
        Order orderB = orderRepository.save(fixtureO.orderRequestB.toEntity());
        assertTrue(orderB.getOrderStatus().equals(OrderStatus.CREATED));

    }

    

}
