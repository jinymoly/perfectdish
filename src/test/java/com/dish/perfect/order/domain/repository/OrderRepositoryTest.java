package com.dish.perfect.order.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.order.OrderFixture;
import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private InMemoryOrderRepository orderRepository;

    private OrderFixture fixtureO = new OrderFixture();

    @BeforeEach
    void saveFixture() {
        Map<Integer, List<OrderItem>> table1 = orderRepository.saveOrderMap(fixtureO.orderRequest1);
        Map<Integer, List<OrderItem>> table2 = orderRepository.saveOrderMap(fixtureO.orderRequest2);
        Map<Integer, List<OrderItem>> table3 = orderRepository.saveOrderMap(fixtureO.orderRequest3);
    }

    @AfterEach
    void clear() {
        orderRepository.clear();
    }

    @Test
    @DisplayName("최종 주문서 저장 및 모든 주문 및 조회")
    void createFinalOrderAndAllOrders() {

        Map<Integer, List<OrderItem>> orders = orderRepository.getAllOrders();
        assertThat(orders.containsKey(1)).isTrue();
        assertThat(orders.containsKey(2)).isTrue();
        assertThat(orders.containsKey(3)).isTrue();
    }

    @Test
    @DisplayName("테이블 번호 별 주문 조회")
    void getOrdersByTableNo() {
        Map<Integer, List<OrderItem>> table2 = orderRepository.getOrderByTableNo(2);
        assertThat(table2.containsKey(2)).isTrue();
        
    }

}
