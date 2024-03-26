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
public class OrderItemPresentationService {
    
    private final OrderItemRepository orderItemRepository;

    /**
     * 테이블 별 주문 내역
     * @param order
     * @return
     */
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

    /**
     * 아직 서빙 되지 않은 주문 내역
     * @param status
     * @return
     */
    public List<OrderItemResponse> findbyOrderItemStatus(OrderItemStatus status){
        return orderItemRepository.findByOrderItemStatus(status)
                                    .stream()
                                    .filter(order -> order.getOrderItemStatus().equals(OrderItemStatus.CREATED))
                                    .map(OrderItemResponse::fromOrderItemResponse)
                                    .toList();
    }

}
