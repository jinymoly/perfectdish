package com.dish.perfect.order.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.menuBoard.MenuBoardFixture;
import com.dish.perfect.menuBoard.domain.repository.InMemoryMenuBoardRepository;
import com.dish.perfect.order.OrderFixture;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private InMemoryOrderRepository orderRepository;

    @Autowired
    private InMemoryMenuBoardRepository menuBoardRepository;

    private OrderFixture fixtureO = new OrderFixture();
    private MenuBoardFixture fixtureB = new MenuBoardFixture();

    @AfterEach
    void clear() {
        orderRepository.clear();
    }

    @BeforeEach
    void allMenu() {
        menuBoardRepository.addCommonMenu(fixtureB.requestCommonsA());
        menuBoardRepository.addCommonMenu(fixtureB.requestCommonsB());
        menuBoardRepository.addDiscountMenu(fixtureB.requestDiscountsA());
        menuBoardRepository.addDiscountMenu(fixtureB.requestDiscountsB());

        menuBoardRepository.getAllMenus();

    }

    @Test
    @DisplayName("주문 생성시 status.CREATED")
    void createOrder() {
        Order orderA = orderRepository.createOrder(fixtureO.orderRequestA);
        Order orderB = orderRepository.createOrder(fixtureO.orderRequestB);

        assertEquals(orderA.getStatus(), OrderStatus.CREATED);
        assertEquals(orderB.getStatus(), OrderStatus.CREATED);

    }

    @Test
    @DisplayName("테이블 번호로 주문 목록 반환")
    void allOrders() {
        orderRepository.createOrder(fixtureO.orderRequestB);
        orderRepository.createOrder(fixtureO.orderRequestE);

        List<Order> expect = orderRepository.getOrders(3);

        for (Order order : expect) {
            assertEquals(3, order.getTableNo());
        }

    }

    @Test
    @DisplayName("status로 조회")
    void ordersByStatys() {
        Order orderA = orderRepository.createOrder(fixtureO.orderRequestB);
        Order orderB = orderRepository.createOrder(fixtureO.orderRequestE);

        assertEquals(orderA.getStatus(), orderB.getStatus());

    }

    @Test
    @DisplayName("주문 추가 확인")
    void addOrder() {
        Order orderA = orderRepository.createOrder(fixtureO.orderRequestD);
        List<Order> order1 = orderRepository.getOrders(3);
        Order orderB = orderRepository.createOrder(fixtureO.orderRequestB);
        List<Order> order2 = orderRepository.getOrders(3);

        assertTrue(order2.stream().allMatch(order -> order.getTableNo() == 3));

    }

    @Test
    @DisplayName("status로 조회시 해당 목록 없음")
    void orderByStatusButNull() {
        Order orderA = orderRepository.createOrder(fixtureO.orderRequestB);
        Order orderB = orderRepository.createOrder(fixtureO.orderRequestE);

        GlobalException exception = assertThrows(GlobalException.class, () -> {
            orderRepository.getOrderByStatus(OrderStatus.COMPLETED);
        });
        assertEquals("해당 주문 목록이 없습니다.", exception.getMessage());
    }

}
