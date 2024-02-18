package com.dish.perfect.order.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.internal.util.logging.Log_.logger;
import org.springframework.stereotype.Service;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.repository.OrderRepository;
import com.dish.perfect.order.dto.request.OrderRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;

    // 모든 주문을 마쳤을 때 - status를 complete로 변경 후 합계
    public void conFirmOrderwithTotalPrice(OrderRequest request){
        List<Order> orders = orderRepository.getAllOrders();
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Order> finalOrder = new ArrayList<>();

        for(Order order : orders){
                totalPrice = totalPrice.add(order.getTotalPrice());
            order.calculateFinalPrice();
            order.addStatus();

            Order finalOrder = new Order(order.getTableNo(), order.getMenuName(), order.getPrice(), order.getCount(), order.getStatus(), order.isDiscount());
            finalOrder.add(finalOrder);
        }
    }

    // 테이블 별 주문을 반환
    public List<Order> getOrderListByTableNu(int tableNo){
        List<Order> orders = orderRepository.getAllOrders();
        for(Order order : orders){
            if(order.getTableNo() == tableNo){
                List<Order> returnList = new ArrayList<>();
                returnList.add(order);
            }
        }
        return orders;
    }

    // 서빙했으면 status를 COMPLETED로 변경 
    public void updateOrderStatus(String menuName){
        List<Order> orders = orderRepository.getAllOrders();
        for(Order order : orders){
            if(order.getMenuName() == menuName){
                order.addStatus();
            }
        }
    }

}
