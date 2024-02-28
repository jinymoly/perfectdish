package com.dish.perfect.order.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class InMemoryOrderRepository implements OrderRepository {

    Map<Integer, List<OrderItem>> orderMap = new ConcurrentHashMap<>();

    @Override
    public Map<Integer, List<OrderItem>> saveOrderMap(OrderRequest orderRequest) {
        List<OrderItem> orderItems = orderRequest.getOrderItems();
        int tableNo = orderRequest.getTableNo();

        if(!orderMap.containsKey(tableNo)){
            orderMap.put(tableNo, new ArrayList<>()); 
        }
        List<OrderItem> orderList = orderMap.get(tableNo);
        orderList.addAll(orderItems);
        
        log.info("saveOrderMap {}", orderMap);
        return orderMap;
    }

    @Override
    public Map<Integer, List<OrderItem>> getOrderByTableNo(int tableNo) {
        Map<Integer, List<OrderItem>> orderByTableNo = new HashMap<>();

        for(Map.Entry<Integer, List<OrderItem>> entry : orderMap.entrySet()){
            Integer keyTableNo = entry.getKey();
            List<OrderItem> valueItems = entry.getValue();

            for(OrderItem orderItem : valueItems){
                if(orderItem.getTableNo() == tableNo){
                    if(orderByTableNo.containsKey(keyTableNo)){
                        orderByTableNo.get(keyTableNo).add(orderItem);
                    } else {
                        List<OrderItem> newOrderItems = new ArrayList<>();
                        newOrderItems.add(orderItem);
                        orderByTableNo.put(keyTableNo, newOrderItems);
                    }
                }
            }
        }
        log.info("getOrderByTableNo {}", orderByTableNo);
        return orderByTableNo;
    }

    @Override
    public Map<Integer, List<OrderItem>> getAllOrders() {
        if (!orderMap.isEmpty()) {
            log.info("{}", orderMap);
            return orderMap;
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "계산서가 존재하지 않습니다.");
        }
    }

    public void clear() {
        orderMap.clear();
    }

}
