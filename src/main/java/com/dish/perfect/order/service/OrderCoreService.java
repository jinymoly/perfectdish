package com.dish.perfect.order.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.order.domain.Order;
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

    public Order createOrder(OrderRequest orderRequest){
        Order order = orderRequest.toEntity();
        order.addCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
        log.info("created{}", order.getId());
        return order;
    }

    public void completeOrder(Long id){
        Order order = orderRepository.findById(id)
                                        .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 주문이 존재하지 않습니다."));
        order.updateStatus();
        order.addCompletedAt(LocalDateTime.now());
        orderRepository.save(order);
        log.info("{}/{}", order.getId(), order.getStatus());
    }
}
