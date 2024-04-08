package com.dish.perfect.orderItem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;
import com.dish.perfect.orderItem.domain.repository.OrderItemRepository;
import com.dish.perfect.orderItem.dto.response.OrderItemResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemPresentationService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem findOrderItemById(final Long id){
        return orderItemRepository.findById(id)
                                  .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDERITEM, "해당 orderItem이 존재하지 않습니다."));
    }

    public OrderItemResponse getOrderItemInfo(final Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                                                 .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDERITEM, "해당 주문 아이템이 존재하지 않습니다."));
        return OrderItemResponse.fromOrderItemResponse(orderItem);
    }

    /**
     * 테이블 별 주문 아이템
     * 
     * @param order
     * @return
     */
    public List<OrderItemResponse> getOrderItemsByTableNo(String tableNo) {
        return orderItemRepository.findBytableNo(tableNo)
                .stream()
                .map(OrderItemResponse::fromOrderItemResponse)
                .toList();
    }

    /**
     * 테이블 별 주문 내역 중 서빙 되지 않은 주문 아이템
     * 
     * @param order
     * @return
     */
    public List<OrderItemResponse> getAllOrderItem(String tableNo) {
        List<OrderItem> orderItemsBytableNo = orderItemRepository.findBytableNo(tableNo);
        if (!orderItemsBytableNo.isEmpty()) {
            return orderItemsBytableNo.stream()
                                        .filter(o -> o.getOrderItemStatus().equals(OrderItemStatus.CREATED))
                                        .map(OrderItemResponse::fromOrderItemResponse)
                                        .toList();
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDERITEM, "해당 테이블의 메뉴가 모두 서빙되었습니다.");
        }
    }

    /**
     * 아직 서빙 되지 않은 모든 주문 아이템 내역
     * 
     * @param status
     * @return
     */
    public List<OrderItemResponse> findbyOrderItemStatus(OrderItemStatus status) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderItemStatus(status);
        if (!orderItems.isEmpty()) {
            return orderItems.stream()
                    .filter(order -> order.getOrderItemStatus().equals(OrderItemStatus.CREATED))
                    .map(OrderItemResponse::fromOrderItemResponse)
                    .toList();
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDERITEM, "모든 메뉴가 서빙되었습니다.");
        }
    }

    public List<OrderItemResponse> findAllOrderItem() {
        return orderItemRepository.findAll()
                .stream()
                .map(OrderItemResponse::fromOrderItemResponse)
                .toList();
    }

}
