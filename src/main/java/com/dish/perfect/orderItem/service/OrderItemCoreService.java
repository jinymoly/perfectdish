package com.dish.perfect.orderItem.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.service.MenuPresentationService;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.repository.OrderItemRepository;
import com.dish.perfect.orderItem.dto.request.OrderItemRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderItemCoreService {

    private final OrderItemRepository orderItemRepository;
    private final MenuPresentationService menuService;

    public OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
        OrderItem orderItem = orderItemRequest.toEntity();
        orderItem.addCreatedAt(LocalDateTime.now());
        if(menuService.menuNameExists(orderItem.getMenu().getMenuName())){
            orderItemRepository.save(orderItem);
            log.info("{}/주문 완료", orderItem.getMenu().getMenuName());
            return orderItem;
        }else{
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "메뉴를 담을 수 없습니다.");
        }
    }

    public void updateOrderItemStatus(Long id){
        OrderItem orderItem = orderItemRepository.findById(id)
                                                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDERITEM, "주문 아이템이 존재하지 않습니다."));
        orderItem.markOrderItemStatusAsCompleted();
        orderItemRepository.save(orderItem);
        log.info("{}/{}", orderItem.getId(), orderItem.getOrderItemStatus());
    }
}
