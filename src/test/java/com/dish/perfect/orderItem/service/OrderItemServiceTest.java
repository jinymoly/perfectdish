package com.dish.perfect.orderItem.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.menuBoard.MenuBoardFixture;
import com.dish.perfect.menuBoard.domain.repository.InMemoryMenuBoardRepository;
import com.dish.perfect.orderItem.OrderItemFixture;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.repository.InMemoryOrderItemRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class OrderItemServiceTest {

    @Autowired
    private OrderItemService orderService;

    @Autowired
    private InMemoryOrderItemRepository orderRepository;

    @Autowired
    private InMemoryMenuBoardRepository menuBoardRepository;

    private OrderItemFixture fixtureO = new OrderItemFixture();
    private MenuBoardFixture fixtureB = new MenuBoardFixture();

    @BeforeEach
    void allMenu() {
        menuBoardRepository.addCommonMenu(fixtureB.requestCommonsA());
        menuBoardRepository.addCommonMenu(fixtureB.requestCommonsB());
        menuBoardRepository.addCommonMenu(fixtureB.requestCommonsC());

        menuBoardRepository.addDiscountMenu(fixtureB.requestDiscountsA());
        menuBoardRepository.addDiscountMenu(fixtureB.requestDiscountsB());
        menuBoardRepository.addDiscountMenu(fixtureB.requestDiscountsC());

        menuBoardRepository.getAllMenus();

    }

    @Test
    @DisplayName("서브 미완료 주문 조회")
    void getOrderByItemstatus() {
        orderRepository.createOrder(fixtureO.orderItemRequestA);
        orderRepository.createOrder(fixtureO.orderItemRequestB);
        orderRepository.createOrder(fixtureO.orderItemRequestC);
        log.info("all:{}", orderRepository.getAllOrders());

        Optional<List<OrderItem>> notServedOrders = orderService.getNotServedOrders(3);
        log.info("notServedOrdersT={}", notServedOrders);
    }

    @Test
    @DisplayName("서브가 모두 완료되어 미완료 서브 반환시 - 모든 음식이 나왔습니다.")
    void getOrderWithCompleteServed() {
        OrderItem orderTest = orderRepository.createOrder(fixtureO.orderItemRequestC);
        OrderItem completeServed = orderService.completeServed(orderTest);
        log.info("status={}", completeServed.getItemstatus());

        Optional<List<OrderItem>> notServedOrders = orderService.getNotServedOrders(3);
        
        assertTrue(notServedOrders.isEmpty());
    }
}
