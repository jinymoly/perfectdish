package com.dish.perfect.orderItem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.internal.util.logging.Log_.logger;
import org.springframework.stereotype.Service;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;
import com.dish.perfect.orderItem.domain.repository.OrderItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {
    
    private final OrderItemRepository orderRepository;

    // 테이블 별 주문을 반환
    public List<OrderItem> getOrderListByTableNo(int tableNo){
        List<OrderItem> orders = orderRepository.getAllOrders();
        List<OrderItem> returnList = new ArrayList<>();
        for(OrderItem order : orders){
            if(order.getTableNo() == tableNo){
                returnList.add(order);
            }
            new GlobalException(ErrorCode.NOT_FOUND_ORDER, order.getTableNo()+"번 테이블 주문이 존재하지 않습니다.");
        }
        return returnList;
    }

    // tableNo completed 되지 않은 목록 반환 
    public Optional<List<OrderItem>> getNotServedOrders(int tableNo){
        List<OrderItem> allOrders = orderRepository.getAllOrders();
        List<OrderItem> notServedOrders = new ArrayList<>();
        
        for(OrderItem item : allOrders){
            if(item.getTableNo() == tableNo && item.getItemstatus() == OrderItemStatus.CREATED){
                notServedOrders.add(item);
            }
        }
        if(notServedOrders.isEmpty()){
            log.info("모든 음식이 나왔습니다.");
            return Optional.empty();
        } else{
            return Optional.of(notServedOrders);
        }
    }

    // 테이블 주문리스트 중 서브 완료된 메뉴 updateStatus
    public OrderItem completeServed(OrderItem orderItem){
        orderItem.updateStatus();
        return orderItem;

    }

}
