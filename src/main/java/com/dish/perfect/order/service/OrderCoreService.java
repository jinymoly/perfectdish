package com.dish.perfect.order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.repository.BillRepository;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderCoreService {

    private final OrderRepository orderRepository;
    private final MenuPresentationService menuService;
    private final MenuRepository menuRepository;
    private final BillRepository billRepository;
    private final BillCoreService billCoreService;

    public Order createOrder(OrderRequest orderRequest) {
        if (menuService.menuNameExists(orderRequest.getMenuName())) {
            Menu menu = menuRepository.findByMenuName(orderRequest.getMenuName());
            Order newOrder = Order.createOrderWithOrderInfo(orderRequest.getTableNo(), menu, orderRequest.getQuantity());
            Bill bill = billCoreService.createBillByTableNo(orderRequest.getTableNo());
            //bill.addOrderToList(newOrder);
            newOrder.addBill(bill);
            newOrder.addCreatedAt(LocalDateTime.now());
            orderRepository.save(newOrder);
            log.info("{}/주문 완료 🥗", newOrder.getOrderInfo().getMenu().getMenuName());
            return newOrder;
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "메뉴를 담을 수 없습니다.");
        }
    }

    /**
     * 추가 주문
     * 
     * @param orderUpdateRequest
     * @return
     */
    public void addOrder(OrderRequest orderRequest) {
        List<Order> findBytableNo = orderRepository.findBytableNo(orderRequest.getTableNo());
        for (Order order : findBytableNo) {
            if (order.getOrderInfo().getMenu().getMenuName().equals(orderRequest.getMenuName())) {
                incrementMenuQuantity(order, orderRequest.getMenuName(), orderRequest.getQuantity());
                order.addModifiedAt(LocalDateTime.now());
            }
        }
    }

    /**
     * orderstatus 완료 
     * @param id
     * @return
     */
    public Order updateOrderStatus(final Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDER, "주문 아이템이 존재하지 않습니다."));
        order.markOrderStatusAsCompleted(OrderStatus.COMPLETED);
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
    public void incrementMenuQuantity(Order order, String menuName, int newQuantity) {
        Integer quantity = order.getOrderInfo().getQuantity();
        quantity += newQuantity;
        order.getOrderInfo().updateOrderInfoQuantity(quantity);

    }

}
