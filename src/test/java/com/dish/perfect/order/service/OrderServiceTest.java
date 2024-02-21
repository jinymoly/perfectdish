package com.dish.perfect.order.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.order.OrderFixture;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.repository.OrderRepository;
import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private OrderFixture fixtureO = new OrderFixture();

    @BeforeEach
    void saveFixture() {
        Map<Integer, List<OrderItem>> table1 = orderRepository.saveOrderMap(fixtureO.orderRequest1);
        Map<Integer, List<OrderItem>> table2 = orderRepository.saveOrderMap(fixtureO.orderRequest2);
        Map<Integer, List<OrderItem>> table3 = orderRepository.saveOrderMap(fixtureO.orderRequest3);
        Map<Integer, List<OrderItem>> table4 = orderRepository.saveOrderMap(fixtureO.orderRequest3);
    }   

    @AfterEach
    void clear(){
        orderService.clear();
    }
    @Test
    @DisplayName("최종 주문서 생성")
    void applyFinalOrderByOrders(){
        Map<Integer, Order> finalOrder2 = orderService.createOrderMap(fixtureO.orderRequest2);
        Map<Integer, Order> finalOrder3 = orderService.createOrderMap(fixtureO.orderRequest3);
        
        Map<Integer, Order> allOrders = orderService.allOrders();
        
        assertThat(allOrders.containsKey(2)).isTrue();
        assertThat(allOrders.containsKey(3)).isTrue();

    }

    @Test
    @DisplayName("countByDuplicatedMenuName() 주문서 취합 시 메뉴 카운트")
    void menuCountFromOrdersByTableNo(){
        List<OrderItem> orderItemsFixture3 = fixtureO.orderItemsFixture3D();
        
        int countByDuplicatedMenuNameCh = orderService.countByDuplicatedMenuName(orderItemsFixture3, "로스트 치킨");
        int countByDuplicatedMenuNameBe = orderService.countByDuplicatedMenuName(orderItemsFixture3, "뵈프 부르기뇽");

        assertThat(countByDuplicatedMenuNameCh).isEqualTo(2);
        assertThat(countByDuplicatedMenuNameBe).isEqualTo(2);
    }

    @Test
    @DisplayName("주문서 취합 시 finalPrice 계산")
    void calculateFinalPrice(){
        Map<Integer, Order> order1 = orderService.createOrderMap(fixtureO.orderRequest1);

        BigDecimal finalPrice = BigDecimal.ZERO;
        for(Map.Entry<Integer, Order> entry : order1.entrySet()){
            finalPrice = entry.getValue().getFinalPrice();
        }
        assertThat(finalPrice).isEqualTo(new BigDecimal(61000));
    }

}
