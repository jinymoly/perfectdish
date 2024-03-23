package com.dish.perfect.orderItem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.orderItem.domain.OrderItemStatus;
import com.dish.perfect.orderItem.domain.repository.OrderItemRepository;
import com.dish.perfect.orderItem.dto.response.OrderItemResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderItemPresentationService {
    
    private final OrderItemRepository orderItemRepository;

    public List<OrderItemResponse> getOrderItemsByTableNo(Order order){
        return orderItemRepository.findByOrder(order)
                                    .stream()
                                    .map(OrderItemResponse::fromOrderItemResponse)
                                    .toList();
    }

    public List<OrderItemResponse> getAllOrderItem(Order order){
        return orderItemRepository.findAll()
                                    .stream()
                                    .map(OrderItemResponse::fromOrderItemResponse)
                                    .toList();
    }

    public List<OrderItemResponse> findbyOrderItemStatus(OrderItemStatus status){
        return orderItemRepository.findByOrderItemStatus(status)
                                    .stream()
                                    .map(OrderItemResponse::fromOrderItemResponse)
                                    .toList();
    }

}
