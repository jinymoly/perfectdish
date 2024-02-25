package com.dish.perfect.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.domain.repository.OrderRepository;
import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public Map<Integer, Order> createOrderMap(OrderRequest orderRequest) {
        int tableNo = orderRequest.getTableNo();
        Map<Integer, List<OrderItem>> orderByTableNo = orderRepository.getOrderByTableNo(tableNo);

        Map<Integer, Order> newOrderMapWithFinalPrice = new ConcurrentHashMap<>();
        List<OrderItem> finalOrderItems = new ArrayList<>();

        for (Map.Entry<Integer, List<OrderItem>> entry : orderByTableNo.entrySet()) {
            List<OrderItem> orders = entry.getValue();
            for (OrderItem order : orders) {
                int countByDuplicatedMenuName = countByDuplicatedMenuName(orders, order.getMenuName());
                OrderItem newOrder = OrderItem.builder()
                                                .tableNo(tableNo)
                                                .menuName(order.getMenuName())
                                                .price(order.getPrice())
                                                .count(countByDuplicatedMenuName)
                                                .totalPrice(order.addTotalPrice())
                                                .isDiscounted(order.isDiscounted())
                                                .itemstatus(OrderItemStatus.COMPLETED)
                                                .build();
                finalOrderItems.add(newOrder);
            }
        }

        BigDecimal finalPrice = calculateFinalPrice(finalOrderItems);

        Order finalOrder = Order.builder()
                                .tableNo(tableNo)
                                .orderList(finalOrderItems)
                                .finalPrice(finalPrice)
                                .status(OrderStatus.NOTSERVED)
                                .build();
        
        newOrderMapWithFinalPrice.put(tableNo, finalOrder);
        return newOrderMapWithFinalPrice;
    }

    public Map<Integer, Order> allOrders() {
        Map<Integer, List<OrderItem>> allOrdersFromRepo = orderRepository.getAllOrders();
        Map<Integer, Order> convertTypeByRepoMap = convertTypeByRepoMap(allOrdersFromRepo);
        log.info("allFinalOrders{}", convertTypeByRepoMap);
        return convertTypeByRepoMap;
    }

    //private
    public int countByDuplicatedMenuName(List<OrderItem> orders, String menuName) {
        int duplicatedCount = 0;
        for (OrderItem order : orders) {
            if (order.getMenuName().equals(menuName)) {
                duplicatedCount += order.getCount();
            }
        }
        return duplicatedCount;
    }

    //private
    public BigDecimal calculateFinalPrice(List<OrderItem> orders) {
        BigDecimal finalPrice = BigDecimal.ZERO;
        for (OrderItem order : orders) {
            finalPrice = finalPrice.add(order.getTotalPrice());
        }
        return finalPrice;
    }

    private Map<Integer, Order> convertTypeByRepoMap(Map<Integer, List<OrderItem>> orderItemListMap) {
        Map<Integer, Order> finalMap = new HashMap<>();
        for (Map.Entry<Integer, List<OrderItem>> entry : orderItemListMap.entrySet()) {
            Integer tableNo = entry.getKey();
            List<OrderItem> orders = entry.getValue();

            BigDecimal finalPrice = calculateFinalPrice(orders);
            Order finalOrder = Order.builder()
                    .tableNo(tableNo)
                    .orderList(orders)
                    .finalPrice(finalPrice)
                    .status(OrderStatus.COMPLETED)
                    .build();
            finalMap.put(tableNo, finalOrder);
        }
        return finalMap;
    }

    public void clear() {
        orderRepository.clear();
    }
}
