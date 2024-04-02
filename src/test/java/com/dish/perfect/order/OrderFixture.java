package com.dish.perfect.order;

import java.util.ArrayList;
import java.util.List;

import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.orderItem.OrderItemFixture;
import com.dish.perfect.orderItem.domain.OrderItem;


public class OrderFixture {
    
    private OrderItemFixture orderItemFixture = new OrderItemFixture();
    
    private OrderItem orderItemA = orderItemFixture.orderItemRequestA.toEntity();
    private OrderItem orderItemB = orderItemFixture.orderItemRequestB.toEntity();
    private OrderItem orderItemC = orderItemFixture.orderItemRequestC.toEntity();
    private OrderItem orderItemD = orderItemFixture.orderItemRequestD.toEntity();
    private OrderItem orderItemE = orderItemFixture.orderItemRequestE.toEntity();
    private OrderItem orderItemF = orderItemFixture.orderItemRequestF.toEntity();
    private OrderItem orderItemG = orderItemFixture.orderItemRequestG.toEntity();
    private OrderItem orderItemH = orderItemFixture.orderItemRequestH.toEntity();
    private OrderItem orderItemI = orderItemFixture.orderItemRequestI.toEntity();
    private OrderItem orderItemJ = orderItemFixture.orderItemRequestJ.toEntity();
    
    public OrderRequest orderRequest1 = OrderRequest.builder()
                                                    .tableNo("2")
                                                    .orderItems(orderItemsT2())
                                                    .status(OrderStatus.NOTSERVED)
                                                    .build();

    public OrderRequest orderRequest2 = OrderRequest.builder()
                                                    .tableNo("3")
                                                    .orderItems(orderItemsT3())
                                                    .status(OrderStatus.NOTSERVED)
                                                    .build();
    public OrderRequest orderRequest3 = OrderRequest.builder()
                                                    .tableNo("7")
                                                    .orderItems(orderItemsT7())
                                                    .status(OrderStatus.NOTSERVED)
                                                    .build();
    public OrderRequest orderRequest3D = OrderRequest.builder()
                                                    .tableNo("1")
                                                    .orderItems(orderItemsT7andT1())
                                                    .status(OrderStatus.NOTSERVED)
                                                    .build();

    
    public List<OrderItem> orderItemsT2(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemA);
        orderItems.add(orderItemE);
        return orderItems;
    }

    public List<OrderItem> orderItemsT3(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemB);
        orderItems.add(orderItemC);
        orderItems.add(orderItemD);
        return orderItems;
    }

    public List<OrderItem> orderItemsT7(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemF);
        orderItems.add(orderItemG);
        return orderItems;
    }

    public List<OrderItem> orderItemsT7andT1(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemH);
        orderItems.add(orderItemI);
        orderItems.add(orderItemJ);
        return orderItems;
    }

    
}
