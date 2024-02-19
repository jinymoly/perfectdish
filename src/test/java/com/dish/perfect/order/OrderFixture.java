package com.dish.perfect.order;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

public class OrderFixture {

    public OrderRequest orderRequest1 = OrderRequest.builder().tableNo(1).orderItems(orderItemsFixture1()).build();
    public OrderRequest orderRequest2 = OrderRequest.builder().tableNo(2).orderItems(orderItemsFixture2()).build();
    public OrderRequest orderRequest3 = OrderRequest.builder().tableNo(3).orderItems(orderItemsFixture3()).build();

    public OrderItem orderItemA(){
        return OrderItem.builder()
                        .tableNo(1)
                        .menuName("프랑스식 양 샴꼬")
                        .count(2)
                        .price(13000)
                        .isDiscount(false)
                        .totalPrice(new BigDecimal(26000))
                        .itemstatus(OrderItemStatus.CREATED)
                        .build();
    }
    public OrderItem orderItemB(){
        return OrderItem.builder()
                        .tableNo(3)
                        .menuName("로스트 치킨")
                        .count(1)
                        .price(28000)
                        .isDiscount(false)
                        .totalPrice(new BigDecimal(28000))
                        .itemstatus(OrderItemStatus.CREATED)
                        .build();
    }

    public OrderItem orderItemC() {
        return OrderItem.builder()
                        .tableNo(2)
                        .menuName("그라탕")
                        .count(2)
                        .price(15000)
                        .isDiscount(true)
                        .totalPrice(new BigDecimal(28500))
                        .itemstatus(OrderItemStatus.CREATED)
                        .build();
    }
    
    public OrderItem orderItemD() {
        return OrderItem.builder()
                        .tableNo(2)
                        .menuName("크림 소스 파스타")
                        .count(2)
                        .price(18000)
                        .isDiscount(true)
                        .totalPrice(new BigDecimal(34200))
                        .itemstatus(OrderItemStatus.CREATED)
                        .build();
    }

    public OrderItem orderItemE() {
        return OrderItem.builder()
                        .tableNo(1)
                        .menuName("스테이크")
                        .count(1)
                        .price(35000)
                        .isDiscount(true)
                        .totalPrice(new BigDecimal(33250))
                        .itemstatus(OrderItemStatus.CREATED)
                        .build();
    }

    public List<OrderItem> orderItemsFixture1(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemA());
        orderItems.add(orderItemE());
        return orderItems;
    }

    public List<OrderItem> orderItemsFixture2(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemC());
        orderItems.add(orderItemD());
        return orderItems;
    }

    public List<OrderItem> orderItemsFixture3(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemB());
        return orderItems;
    }

    
}
