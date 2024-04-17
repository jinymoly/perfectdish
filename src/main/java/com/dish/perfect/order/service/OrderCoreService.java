package com.dish.perfect.order.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
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

    public Order createOrder(OrderRequest orderRequest) {
        Order order = orderRequest.toEntity();
        if(menuService.menuNameExists(order.getMenu().getMenuName())){
            order.addCreatedAt(LocalDateTime.now());
            orderRepository.save(order);
            log.info("{}/주문 완료 🥗", order.getMenu().getMenuName());
            return order;
        }else{
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "메뉴를 담을 수 없습니다.");
        }
    }

    public Order updateOrderStatus(final Long id){
        Order order = orderRepository.findById(id)
                                                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDER, "주문 아이템이 존재하지 않습니다."));
        order.markOrderStatusAsCompleted(OrderStatus.COMPLETED);
        log.info("{}/{}", order.getId(), order.getOrderStatus());
        return order;
    }
}
