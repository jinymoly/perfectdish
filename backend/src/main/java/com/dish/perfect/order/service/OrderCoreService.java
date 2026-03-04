package com.dish.perfect.order.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.service.BillCoreService;
import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.menu.service.MenuPresentationService;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.domain.repository.OrderRepository;
import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.order.event.OrderAddEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderCoreService {

    private final ApplicationEventPublisher eventPublisher;
    private final OrderRepository orderRepository;
    private final MenuPresentationService menuService;
    private final MenuRepository menuRepository;
    private final BillCoreService billCoreService;

    /**
     * 주문을 추가하면 계속 새로운 order가 생성 된다.
     * 
     * @param orderRequest
     * @return
     */
    public Order createOrder(OrderRequest orderRequest) {
        if (menuService.menuNameExists(orderRequest.getMenuName())) {
            Menu menu = menuRepository.findByMenuName(orderRequest.getMenuName());
            Order newOrder = Order.createOrderWithOrderInfo(orderRequest.getTableNo(), orderRequest.getPhoneNumber(), menu,
                    orderRequest.getQuantity());
            Bill bill = billCoreService.mergeOrdersAndCreateBillByTableNo(orderRequest.getTableNo());
            bill.addOrderToList(newOrder);
            Order savedOrder = orderRepository.save(newOrder);
            eventPublisher.publishEvent(new OrderAddEvent(savedOrder));
            log.info("{}/주문 완료 🥗", newOrder.getOrderInfo().getMenu().getMenuName());
            return newOrder;
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "메뉴를 담을 수 없습니다.");
        }
    }

    /**
     * 테이블 번호가 같고 같은 메뉴가 추가될 때
     * 
     * @param orderUpdateRequest
     * @return
     */
    public void addOrder(OrderRequest orderRequest) {
        List<Order> findBytableNo = orderRepository.findBytableNo(orderRequest.getTableNo());
        for (Order order : findBytableNo) {
            if (order.getOrderInfo().getMenu().getMenuName().equals(orderRequest.getMenuName())) {
                incrementMenuQuantity(order, orderRequest.getMenuName(), orderRequest.getQuantity());
                eventPublisher.publishEvent(new OrderAddEvent(order));
                order.addModifiedAt();
            }
        }
    }

    /**
     * orderstatus 완료
     * 
     * @param id
     * @return
     */
    public Order updateOrderStatus(final Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDER, "주문 아이템이 존재하지 않습니다."));
        order.markOrderStatusAsCompleted(status); // Reusing the method name but passing status
        order.addModifiedAt();
        log.info("{}/{}", order.getId(), order.getOrderStatus());
        return order;
    }

    /**
     * OrderInfo 메뉴 수량 증가
     * 
     * @param order
     * @param menuName
     * @param newQuantity
     * @return
     */
    private void incrementMenuQuantity(Order order, String menuName, int newQuantity) {
        Integer quantity = order.getOrderInfo().getQuantity();
        quantity += newQuantity;
        order.getOrderInfo().updateOrderInfoQuantity(quantity);

    }

    public java.util.List<Order> createBatchOrder(com.dish.perfect.order.dto.request.BatchOrderRequest batchOrderRequest) {
        Bill bill = billCoreService.createNewBill(batchOrderRequest.getTableNo());
        java.util.List<Order> createdOrders = new java.util.ArrayList<>();

        for (com.dish.perfect.order.dto.request.OrderItemRequest item : batchOrderRequest.getItems()) {
            if (menuService.menuNameExists(item.getMenuName())) {
                Menu menu = menuRepository.findByMenuName(item.getMenuName());
                
                // Decrement stock
                menu.decrementStock(item.getQuantity());
                menuRepository.save(menu);
                
                Order newOrder = Order.createOrderWithOrderInfo(batchOrderRequest.getTableNo(), 
                    batchOrderRequest.getPhoneNumber(), menu, item.getQuantity());
                bill.addOrderToList(newOrder);
                Order savedOrder = orderRepository.save(newOrder);
                createdOrders.add(savedOrder);
            }
        }
        
        // Publish event for the last order or a new BatchOrderEvent if needed
        if (!createdOrders.isEmpty()) {
            eventPublisher.publishEvent(new OrderAddEvent(createdOrders.get(createdOrders.size() - 1)));
        }
        
        return createdOrders;
    }

}
