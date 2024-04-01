package com.dish.perfect.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.domain.repository.OrderRepository;
import com.dish.perfect.order.dto.response.OrderResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderPresentationService {

    private final OrderRepository orderRepository;

    /**
     * 단일 order 정보 반환 
     * @param id
     * @return
     */
    public OrderResponse getOrderInfo(final Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 주문이 존재하지 않습니다."));
        return OrderResponse.fromOrderResponse(order);
    }

    public List<OrderResponse> findAll(){
        return orderRepository.findAll()
                                .stream()
                                .map(OrderResponse::fromOrderResponse)
                                .toList();
                                
    }

    /**
     * 테이블 별 모든 주문 내역
     * @param tableNo
     * @return
     */
    public List<OrderResponse> findOrderByTableNo(String tableNo){
        List<Order> orders = orderRepository.findByTableNo(tableNo);
        if(!orders.isEmpty()){
            return orders.stream()
                        .map(OrderResponse::fromOrderResponse)
                        .toList();
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 테이블에 주문 내역이 존재하지 않습니다.");
        }
    }

    /**
     * 아직 서빙 되지 않은 모든 주문 내역
     * @param orderStatus
     * @return
     */
    public List<OrderResponse> findOrderByOrderStatus(OrderStatus orderStatus){
        List<Order> orders = orderRepository.findByOrderStatus(orderStatus);
        if(!orders.isEmpty()){
            return orders.stream()
                        .filter(order -> order.getOrderStatus().equals(OrderStatus.NOTSERVED))
                        .map(OrderResponse::fromOrderResponse)
                        .toList();
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "모든 음식이 서빙되었습니다.");
        }
    }
}
