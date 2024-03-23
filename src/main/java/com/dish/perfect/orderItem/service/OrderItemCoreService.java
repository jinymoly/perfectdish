package com.dish.perfect.orderItem.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.repository.OrderItemRepository;
import com.dish.perfect.orderItem.dto.request.OrderItemRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderItemCoreService {
    
    private final OrderItemRepository orderItemRepository;

    public OrderItem createOrderItem(OrderItemRequest orderItemRequest){
        OrderItem orderItem = orderItemRequest.toEntity();
        OrderItem newOrder = orderItemRepository.save(orderItem);
        log.info("createOrderItem{}\nid:{}\nmenu:{}\ncount:{}\nD:{}/price:{}", 
                        newOrder.getId(), 
                        newOrder.getMenu().getMenuName(),
                        newOrder.getCount(),
                        newOrder.getMenu().isDiscounted(),
                        newOrder.getTotalPrice());
        return newOrder;
    }

    public OrderItem updateOrderItemCount(final OrderItem orderItem, @Valid OrderItemRequest orderItemRequest){
        if(orderItem.getMenu().equals(orderItemRequest.getMenu())){
            orderItem.updateCount(orderItemRequest.getCount());
            return orderItemRepository.save(orderItem);
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "해당 메뉴가 존재하지 않습니다.");
        }

    }
}
