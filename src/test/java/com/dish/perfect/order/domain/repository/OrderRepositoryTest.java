package com.dish.perfect.order.domain.repository;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.order.OrderFixture;
import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class OrderRepositoryTest {

    @Autowired
    private InMemoryOrderRepository orderRepository;

    private OrderFixture fixtureO = new OrderFixture();

    @AfterEach
    void clear() {
        orderRepository.clear();
    }

    @Test
    @DisplayName("최종 주문서 저장")
    void createFinalOrder(){
        orderRepository.saveOrderMap(fixtureO.orderRequest1);
        orderRepository.saveOrderMap(fixtureO.orderRequest2);
        orderRepository.saveOrderMap(fixtureO.orderRequest3);
        
        Map<Integer, OrderItem> orders = orderRepository.getAllOrders();
        log.info("{}", orders);
    }

}
