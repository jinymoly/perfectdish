package com.dish.perfect.orderItem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.orderItem.OrderItemFixture;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;
import com.dish.perfect.orderItem.domain.repository.OrderItemRepository;
import com.dish.perfect.orderItem.dto.response.OrderItemResponse;

@SpringBootTest
public class OrderItemCoreServiceTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderItemCoreService orderItemCoreService;
    @Autowired
    private OrderItemPresentationService orderItemPresentationService;;

    private OrderItemFixture fixtureO = new OrderItemFixture();
    private MenuFixture fixtureM = new MenuFixture();

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

    @AfterEach
    void clear() {
        orderItemRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("테이블 번호로 주문 목록 반환")
    void allOrders() {

        OrderItem orderItemA = orderItemCoreService.createOrderItem(fixtureO.orderItemRequestA);
        OrderItem orderItemB = orderItemCoreService.createOrderItem(fixtureO.orderItemRequestB);
        OrderItem orderItemH = orderItemCoreService.createOrderItem(fixtureO.orderItemRequestH);

        List<OrderItemResponse> tableNo2 = orderItemPresentationService.getOrderItemsByTableNo("2");
        List<OrderItemResponse> tableNo3 = orderItemPresentationService.getOrderItemsByTableNo("7");
        assertEquals(tableNo2.size(), 1);
        assertNotEquals(orderItemA.getTableNo(), orderItemB.getTableNo(), orderItemH.getTableNo());
        assertTrue(tableNo3.size() == 1);

    }

    @Test
    @DisplayName("status.CREATED로 조회")
    void ordersByStatus() {
        OrderItem orderC = orderItemCoreService.createOrderItem(fixtureO.orderItemRequestC);
        OrderItem orderD = orderItemCoreService.createOrderItem(fixtureO.orderItemRequestD);

        assertEquals(orderC.getOrderItemStatus(), OrderItemStatus.CREATED);
        assertEquals(orderC.getOrderItemStatus(), orderD.getOrderItemStatus());

    }

    @Test
    @DisplayName("주문 상태 변경 플로우")
    void addOrder() {
        OrderItem orderF = orderItemCoreService.createOrderItem(fixtureO.orderItemRequestF);
        orderItemCoreService.updateOrderItemStatus(orderF.getId());

        OrderItemResponse result = orderItemPresentationService.getOrderItemInfo(orderF.getId());

        assertTrue(result.getOrderStatus().equals(OrderItemStatus.COMPLETED));
    }

    @Test
    @DisplayName("status.COMPLETED로 조회시 해당 목록 없음")
    void orderByStatusButNull() {
        OrderItem orderG = orderItemCoreService.createOrderItem(fixtureO.orderItemRequestG);
        OrderItem orderE = orderItemCoreService.createOrderItem(fixtureO.orderItemRequestE);

        GlobalException exception = assertThrows(GlobalException.class, 
                                    () -> { orderItemPresentationService.findbyOrderItemStatus(OrderItemStatus.COMPLETED);
                                    });
        assertEquals("모든 메뉴가 서빙되었습니다.", exception.getMessage());
    }
}
