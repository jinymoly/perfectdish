package com.dish.perfect.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.dish.perfect.order.domain.repository.OrderRepository;
import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // TODO
    // 메뉴의 갯수를 반환
    // 주문서들 중 이름이 같은 메뉴를 카운트 
    // 새 주문서 작성
    // 새 주문맵에 finalPrice와 함께 추가 
    public Map<Integer, List<OrderItem>> createOrderMap(OrderRequest orderRequest) {
        Map<Integer, List<OrderItem>> orderByTableNo = orderRepository.getOrderByTableNo(orderRequest.getTableNo());
        Map<Integer, List<OrderItem>> finalOrderMap = new ConcurrentHashMap<>();

        for (Map.Entry<Integer, List<OrderItem>> entry : orderByTableNo.entrySet()) {
            List<OrderItem> orders = entry.getValue();
            for (OrderItem order : orders) {
                int countByDuplicatedMenuName = countByDuplicatedMenuName(orders, order.getMenuName());
                order.updateCount(countByDuplicatedMenuName);
                orders.add(order);
                finalOrderMap.put(order.getTableNo(), orders);
            }
        }
        return finalOrderMap;
    }

    /**
     * 메뉴 갯수 취합
     * 
     * @param orders
     * @param menuName
     * @return
     */
    public int countByDuplicatedMenuName(List<OrderItem> orders, String menuName) {
        int duplicatedCount = 0;
        for (OrderItem order : orders) {
            if (order.getMenuName() == menuName) {
                duplicatedCount += order.getCount();
            }
        }
        return duplicatedCount;
    }

}
