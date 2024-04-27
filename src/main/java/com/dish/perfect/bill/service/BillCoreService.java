package com.dish.perfect.bill.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.repository.BillRepository;
import com.dish.perfect.bill.dto.request.BillRequest;
import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.domain.repository.OrderRepository;
import com.dish.perfect.order.service.OrderPresentationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BillCoreService {
    
    private final BillRepository billRepository;
    private final OrderRepository orderRepository;

    public Bill createBill(BillRequest billRequest){
        Bill bill = billRequest.toBill();
        List<Order> orders = bill.getOrders();
        for(Order o : orders){

            bill.createOrderListWithTableNoFrom(o);
        }
        bill.applyTotalPrice(orders);
        bill.addCreatedAt(LocalDateTime.now());
        billRepository.save(bill);
        log.info("created Bill:{}", bill.getId());
        return bill;
    }

    public void completeAllOrdersInBill(Long id){
        Bill bill = billRepository.findById(id)
                                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_BILL, "해당 청구서가 존재하지 않습니다."));
        bill.updateStatus();
        bill.addCompletedAt(LocalDateTime.now());
        billRepository.save(bill);
        log.info("{}/{}", bill.getId(), bill.getOrderStatus());
    }
    
    /**
     * [F] 테이블 별 주문을 합산  
     * @return
     */
    public List<Map<Menu, Integer>> createFinalOrderMapAndcreateFinalList(String tableNo){
        Map<Menu, Integer> orderMap = new ConcurrentHashMap<>();
        List<Map<Menu, Integer>> finalOrderList = new ArrayList<>();
        List<Order> orders = orderRepository.findByCompletedOrderWithSameTableNo(tableNo, OrderStatus.COMPLETED);
        for(Order o : orders){
            if(o.getTableNo().equals(tableNo)){
                int newQuantity = orderMap.getOrDefault(o.getMenu(), 0) + o.getCount();
                orderMap.put(o.getMenu(), newQuantity);
            }
        }
        finalOrderList.add(orderMap);
        return finalOrderList;
    }

}
