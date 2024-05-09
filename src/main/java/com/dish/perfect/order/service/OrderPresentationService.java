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

    public Order findOrderById(final Long id){
        return orderRepository.findById(id)
                                  .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 order이 존재하지 않습니다."));
    }

    public OrderResponse getOrderInfo(final Long id) {
        Order order = orderRepository.findById(id)
                                                 .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 주문 아이템이 존재하지 않습니다."));
        return OrderResponse.fromOrderResponse(order);
    }

    /**
     * 테이블 별 주문 아이템
     * 
     * @param order
     * @return
     */
    public List<OrderResponse> getOrdersByTableNo(String tableNo) {
        return orderRepository.findBytableNo(tableNo)
                .stream()
                .map(OrderResponse::fromOrderResponse)
                .toList();
    }

    /**
     * 테이블 별 주문 아이템 to Bill
     * 
     * @param order
     * @return
     */
    public List<Order> getOrdersToBillByTableNo(String tableNo) {
        return orderRepository.findBytableNo(tableNo)
                .stream()
                .toList();
    }

    /**
     * 테이블 별 주문 내역 중 서빙 되지 않은 주문 아이템
     * 
     * @param order
     * @return
     */
    public List<OrderResponse> getAllOrder(String tableNo) {
        List<Order> ordersBytableNo = orderRepository.findBytableNo(tableNo);
        if (!ordersBytableNo.isEmpty()) {
            return ordersBytableNo.stream()
                                        .filter(o -> o.getOrderStatus().equals(OrderStatus.CREATED))
                                        .map(OrderResponse::fromOrderResponse)
                                        .toList();
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 테이블의 메뉴가 모두 서빙되었습니다.");
        }
    }

    /**
     * 아직 서빙 되지 않은 모든 주문 아이템 내역
     * 
     * @param status
     * @return
     */
    public List<OrderResponse> findbyOrderStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByOrderStatus(status);
        if (!orders.isEmpty()) {
            return orders.stream()
                    .filter(order -> order.getOrderStatus().equals(OrderStatus.CREATED))
                    .map(OrderResponse::fromOrderResponse)
                    .toList();
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "모든 메뉴가 서빙되었습니다.");
        }
    }

    public List<OrderResponse> findAllOrder() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::fromOrderResponse)
                .toList();
    }

}
