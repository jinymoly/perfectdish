package com.dish.perfect.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.bill.BillFixture;
import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.order.OrderFixture;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.domain.repository.OrderRepository;
import com.dish.perfect.order.dto.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class OrderCoreServiceTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderCoreService orderCoreService;

    @Autowired
    private OrderPresentationService orderPresentationService;;

    private OrderFixture fixtureO = new OrderFixture();
    private MenuFixture fixtureM = new MenuFixture();
    private BillFixture fixtureB = new BillFixture();

    @BeforeEach
    void createMenu() {
        orderRepository.deleteAllInBatch();

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
        Order newOrderA = orderCoreService.createOrder(fixtureO.orderRequestA);
        Order newOrderI = orderCoreService.createOrder(fixtureO.orderRequestI);
        Order newOrderE = orderCoreService.createOrder(fixtureO.orderRequestE);

        assertTrue(newOrderA.getOrderStatus().equals(OrderStatus.CREATED));
        assertTrue(newOrderI.getOrderStatus().equals(OrderStatus.CREATED));
        assertEquals(newOrderA.getOrderStatus(), newOrderE.getOrderStatus());

    }

    @Test
    @DisplayName("테이블 번호로 주문 목록 반환")
    void allOrders() {

        Order orderA = orderCoreService.createOrder(fixtureO.orderRequestA);
        Order orderB = orderCoreService.createOrder(fixtureO.orderRequestB);
        Order orderH = orderCoreService.createOrder(fixtureO.orderRequestH);

        List<OrderResponse> tableNo2 = orderPresentationService.getOrdersByTableNo("2");
        List<OrderResponse> tableNo3 = orderPresentationService.getOrdersByTableNo("7");
        assertEquals(tableNo2.size(), 1);
        assertNotEquals(orderA.getTableNo(), orderB.getTableNo(), orderH.getTableNo());
        assertTrue(tableNo3.size() == 1);

    }

    @Test
    @DisplayName("status.CREATED로 조회")
    void ordersByStatus() {
        Order orderC = orderCoreService.createOrder(fixtureO.orderRequestC);
        Order orderD = orderCoreService.createOrder(fixtureO.orderRequestD);

        assertEquals(orderC.getOrderStatus(), OrderStatus.CREATED);
        assertEquals(orderC.getOrderStatus(), orderD.getOrderStatus());

    }

    @Test
    @DisplayName("주문 상태 변경 플로우")
    void addOrder() {
        Order orderF = orderCoreService.createOrder(fixtureO.orderRequestF);
        orderCoreService.updateOrderStatus(orderF.getId());

        OrderResponse result = orderPresentationService.getOrderInfo(orderF.getId());

        assertTrue(result.getOrderStatus().equals(OrderStatus.COMPLETED));
    }

    @Test
    @DisplayName("추가 주문시 수량 증가")
    void incrementMenuQuantity(){
        orderCoreService.createOrder(fixtureO.orderRequestI);
        orderCoreService.createOrder(fixtureO.orderRequestJ);
        List<OrderResponse> ordersByTableNo1Before = orderPresentationService.getOrdersByTableNo("1");
        int beforeCount = 0;
        for(OrderResponse orderResponse : ordersByTableNo1Before){
            log.info("🐠Before🐠 {}/{}", orderResponse.getMenuName(), orderResponse.getQuantity());
            beforeCount += orderResponse.getQuantity();
        }
        assertEquals(beforeCount, 4);
        orderCoreService.addOrder(fixtureO.orderRequestJ);
        List<OrderResponse> ordersByTableNo1After = orderPresentationService.getOrdersByTableNo("1");
        int afterCount = 0;
        for(OrderResponse orderResponse : ordersByTableNo1After){
            log.info("🐠After🐠 {}/{}", orderResponse.getMenuName(), orderResponse.getQuantity());
            afterCount += orderResponse.getQuantity();
        }
        assertEquals(afterCount, 7);

    }

    @Test
    @DisplayName("status.COMPLETED로 조회시 해당 목록 없음")
    void orderByStatusButNull() {
        Order orderG = orderCoreService.createOrder(fixtureO.orderRequestG);
        Order orderE = orderCoreService.createOrder(fixtureO.orderRequestE);

        GlobalException exception = assertThrows(GlobalException.class, 
                                    () -> { orderPresentationService.findbyOrderStatus(OrderStatus.COMPLETED);
                                    });
        assertEquals("모든 메뉴가 서빙되었습니다.", exception.getMessage());
    }
}
