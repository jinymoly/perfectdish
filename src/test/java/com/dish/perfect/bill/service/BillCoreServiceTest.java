package com.dish.perfect.bill.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.bill.BillFixture;
import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.order.OrderFixture;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.service.OrderCoreService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BillCoreServiceTest {

    @Autowired
    private BillCoreService billCoreService;
    
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private OrderCoreService orderCoreService;

    private MenuFixture menuFixture = new MenuFixture();
    private BillFixture billFixture = new BillFixture();
    private OrderFixture orderFixture = new OrderFixture();

    @BeforeEach
    void clear(){
        menuRepository.save(menuFixture.fixRequestA().toEntity());
        menuRepository.save(menuFixture.fixRequestB().toEntity());
        menuRepository.save(menuFixture.fixRequestC().toEntity());
        menuRepository.save(menuFixture.fixRequestD().toEntity());
        menuRepository.save(menuFixture.fixRequestE().toEntity());
        menuRepository.save(menuFixture.fixRequestF().toEntity());
        menuRepository.save(menuFixture.fixRequestG().toEntity());
        menuRepository.save(menuFixture.fixRequestH().toEntity());
        menuRepository.save(menuFixture.fixRequestI().toEntity());

        orderCoreService.createOrder(orderFixture.orderRequestA);
        orderCoreService.createOrder(orderFixture.orderRequestB);
        orderCoreService.createOrder(orderFixture.orderRequestC);
        orderCoreService.createOrder(orderFixture.orderRequestD);
        orderCoreService.createOrder(orderFixture.orderRequestE);
        orderCoreService.createOrder(orderFixture.orderRequestF);
        orderCoreService.createOrder(orderFixture.orderRequestG);
        orderCoreService.createOrder(orderFixture.orderRequestH);
        orderCoreService.createOrder(orderFixture.orderRequestI);
        orderCoreService.createOrder(orderFixture.orderRequestJ);
        
    }

    @Test
    @DisplayName("최종 주문서 생성과 동시에 최종 가격을 계산한다")
    void createBillWithSameTableNo(){
        Bill billByTableNo7 = billCoreService.createBill(billFixture.orderRequestTableNo7);
        for(Order order : billByTableNo7.getOrders()){
            log.info("billByTableNo7:{}/{}/{}/{}/{}", order.getOrderInfo().getMenu().getMenuName(), 
                                                                order.getOrderInfo().getMenu().getPrice(), 
                                                                order.getOrderInfo().getMenu().isDiscounted(), 
                                                                order.getOrderInfo().getQuantity(), 
                                                                order.getOrderStatus());
        }
        log.info("tableNo:{}", billByTableNo7.getTableNo());
        log.info("TotalPrice:{}", billByTableNo7.getTotalPrice());

        assertEquals(billByTableNo7.getTableNo(), "7");
        
    }
    @Test
    @DisplayName("bill의 status가 하나라도 completed가 아니면 최종 주문서 업데이트 불가")
    void cannotUpdateFinalBillWithIncompleteOrders(){
        Bill billTableNo3 = billCoreService.createBill(billFixture.orderRequestTableNo3);
        log.info("💡 [BEFORE] billstatus is {}", billTableNo3.getBillStatus());
        
        GlobalException exception = assertThrows(GlobalException.class, 
                                                 () -> { 
                                                    billCoreService.completeAllOrdersInBill(billTableNo3.getId());
                                                });
        assertEquals("아직 서빙되지 않은 메뉴가 있습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("모든 주문 서빙 완료시 최종 주문서 업데이트")
    void updateBill(){
        Bill billTableNo3 = billCoreService.createBill(billFixture.orderRequestTableNo3);
        log.info("💡 [BEFORE] billstatus is {}", billTableNo3.getBillStatus());

        for(Order order : billTableNo3.getOrders()){
            orderCoreService.updateOrderStatus(order.getId());
        }
        Bill completeAllOrdersInBill = billCoreService.completeAllOrdersInBill(billTableNo3.getId());
        
        log.info("💡 [AFTER] billstatus is {}", completeAllOrdersInBill.getBillStatus());
        assertEquals(completeAllOrdersInBill.getBillStatus(), BillStatus.COMPLETED);

    }

}
