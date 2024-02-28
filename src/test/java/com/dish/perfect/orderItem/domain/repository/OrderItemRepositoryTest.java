package com.dish.perfect.orderItem.domain.repository;

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
import com.dish.perfect.orderItem.OrderItemFixture;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

@SpringBootTest
public class OrderItemRepositoryTest {

    @Autowired
    private InMemoryOrderItemRepository orderItemRepository;

    @Autowired
    private InMemoryMenuBoardRepository menuBoardRepository;

    private OrderItemFixture fixtureO = new OrderItemFixture();
    private MenuBoardFixture fixtureB = new MenuBoardFixture();

    @AfterEach
    void clear() {
        orderItemRepository.clear();
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
        OrderItem orderA = orderItemRepository.createOrder(fixtureO.orderItemRequestA);
        OrderItem orderB = orderItemRepository.createOrder(fixtureO.orderItemRequestB);

        assertEquals(orderA.getItemstatus(), OrderItemStatus.CREATED);
        assertEquals(orderB.getItemstatus(), OrderItemStatus.CREATED);

    }

    @Test
    @DisplayName("테이블 번호로 주문 목록 반환")
    void allOrders() {
        orderItemRepository.createOrder(fixtureO.orderItemRequestB);
        orderItemRepository.createOrder(fixtureO.orderItemRequestE);

        List<OrderItem> expect = orderItemRepository.getOrders(3);

        for (OrderItem order : expect) {
            assertEquals(3, order.getTableNo());
        }

    }

    @Test
    @DisplayName("status로 조회")
    void ordersByStatus() {
        OrderItem orderA = orderItemRepository.createOrder(fixtureO.orderItemRequestB);
        OrderItem orderB = orderItemRepository.createOrder(fixtureO.orderItemRequestE);

        assertEquals(orderA.getItemstatus(), orderB.getItemstatus());

    }

    @Test
    @DisplayName("주문 추가 확인")
    void addOrder() {
        OrderItem orderA = orderItemRepository.createOrder(fixtureO.orderItemRequestD);
        List<OrderItem> order1 = orderItemRepository.getOrders(3);
        OrderItem orderB = orderItemRepository.createOrder(fixtureO.orderItemRequestB);
        List<OrderItem> order2 = orderItemRepository.getOrders(3);

        assertTrue(order2.stream().allMatch(order -> order.getTableNo() == 3));

    }

    @Test
    @DisplayName("status로 조회시 해당 목록 없음")
    void orderByStatusButNull() {
        OrderItem orderA = orderItemRepository.createOrder(fixtureO.orderItemRequestB);
        OrderItem orderB = orderItemRepository.createOrder(fixtureO.orderItemRequestE);

        GlobalException exception = assertThrows(GlobalException.class, () -> {
            orderItemRepository.getOrderByStatus(OrderItemStatus.COMPLETED);
        });
        assertEquals("해당 주문 목록이 없습니다.", exception.getMessage());
    }

}
