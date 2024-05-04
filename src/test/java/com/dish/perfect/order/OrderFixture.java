package com.dish.perfect.order;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderInfo;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.dto.request.OrderRequest;

public class OrderFixture {

    private MenuFixture menuFixture = new MenuFixture();

    private Menu menuA = menuFixture.fixRequestA().toEntity();
    private Menu menuB = menuFixture.fixRequestB().toEntity();
    private Menu menuC = menuFixture.fixRequestC().toEntity();
    private Menu menuD = menuFixture.fixRequestD().toEntity();
    private Menu menuE = menuFixture.fixRequestE().toEntity();
    private Menu menuF = menuFixture.fixRequestF().toEntity();
    private Menu menuG = menuFixture.fixRequestG().toEntity();
    private Menu menuH = menuFixture.fixRequestH().toEntity();

    public OrderRequest orderRequestA = OrderRequest.builder()
                                                    .tableNo("2")
                                                    .menuName(menuA.getMenuName())
                                                    .quantity(2)
                                                    .status(OrderStatus.CREATED)
                                                    .build();

    public OrderRequest orderRequestB = OrderRequest.builder()
                                                    .tableNo("3")
                                                    .menuName(menuB.getMenuName())
                                                    .quantity(1)
                                                    .status(OrderStatus.CREATED)
                                                    .build();

    public OrderRequest orderRequestC = OrderRequest.builder()
                                                    .tableNo("3")
                                                    .menuName(menuC.getMenuName())
                                                    .quantity(3)
                                                    .status(OrderStatus.CREATED)
                                                    .build();

    public OrderRequest orderRequestD = OrderRequest.builder()
                                                    .tableNo("3")
                                                    .menuName(menuD.getMenuName())
                                                    .quantity(5)
                                                    .status(OrderStatus.CREATED)
                                                    .build();

    public OrderRequest orderRequestE = OrderRequest.builder()
                                                    .tableNo("2")
                                                    .menuName(menuE.getMenuName())
                                                    .quantity(2)
                                                    .status(OrderStatus.CREATED)
                                                    .build();

    public OrderRequest orderRequestF = OrderRequest.builder()
                                                    .tableNo("7")
                                                    .menuName(menuF.getMenuName())
                                                    .quantity(2)
                                                    .status(OrderStatus.COMPLETED)
                                                    .build();

    public OrderRequest orderRequestG = OrderRequest.builder()
                                                    .tableNo("7")
                                                    .menuName(menuG.getMenuName())
                                                    .quantity(3)
                                                    .status(OrderStatus.CREATED)
                                                    .build();

    public OrderRequest orderRequestH = OrderRequest.builder()
                                                    .tableNo("7")
                                                    .menuName(menuH.getMenuName())
                                                    .quantity(1)
                                                    .status(OrderStatus.COMPLETED)
                                                    .build();

    public OrderRequest orderRequestI = OrderRequest.builder()
                                                    .tableNo("1")
                                                    .menuName(menuH.getMenuName())
                                                    .quantity(1)
                                                    .status(OrderStatus.COMPLETED)
                                                    .build();

    public OrderRequest orderRequestJ = OrderRequest.builder()
                                                    .tableNo("1")
                                                    .menuName(menuB.getMenuName())
                                                    .quantity(3)
                                                    .status(OrderStatus.CREATED)
                                                    .build();


    public Order orderForBillA = Order.builder()
                                        .tableNo("2")
                                        .orderInfo(OrderInfo.of(menuA, 2))
                                        .orderStatus(OrderStatus.CREATED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    
    public Order orderForBillB = Order.builder()
                                        .tableNo("3")
                                        .orderInfo(OrderInfo.of(menuB, 1))
                                        .orderStatus(OrderStatus.CREATED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    public Order orderForBillC = Order.builder()
                                        .tableNo("3")
                                        .orderInfo(OrderInfo.of(menuC, 3))
                                        .orderStatus(OrderStatus.CREATED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    
    public Order orderForBillD = Order.builder()
                                        .tableNo("3")
                                        .orderInfo(OrderInfo.of(menuD, 5))
                                        .orderStatus(OrderStatus.CREATED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    public Order orderForBillE = Order.builder()
                                        .tableNo("2")
                                        .orderInfo(OrderInfo.of(menuE, 2))
                                        .orderStatus(OrderStatus.CREATED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    public Order orderForBillF = Order.builder()
                                        .tableNo("7")
                                        .orderInfo(OrderInfo.of(menuF, 2))
                                        .orderStatus(OrderStatus.COMPLETED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    
    public Order orderForBillG = Order.builder()
                                        .tableNo("7")
                                        .orderInfo(OrderInfo.of(menuG, 3))
                                        .orderStatus(OrderStatus.CREATED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    
    public Order orderForBillH = Order.builder()
                                        .tableNo("7")
                                        .orderInfo(OrderInfo.of(menuH, 1))
                                        .orderStatus(OrderStatus.COMPLETED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    public Order orderForBillI = Order.builder()
                                        .tableNo("1")
                                        .orderInfo(OrderInfo.of(menuA, 1))
                                        .orderStatus(OrderStatus.COMPLETED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();
    
    public Order orderForBillJ = Order.builder()
                                        .tableNo("1")
                                        .orderInfo(OrderInfo.of(menuB, 3))
                                        .orderStatus(OrderStatus.CREATED)
                                        .createdAt(LocalDateTime.now())
                                        .modifiedAt(LocalDateTime.now())
                                        .build();

    public List<Order> ordersForBillT2(){
        List<Order> orders = new ArrayList<>();
        orders.add(orderForBillA);
        orders.add(orderForBillE);
        return orders;
    }

    public List<Order> ordersForBillT3(){
        List<Order> orders = new ArrayList<>();
        orders.add(orderForBillB);
        orders.add(orderForBillC);
        orders.add(orderForBillD);
        return orders;
    }

    public List<Order> ordersForBillT7(){
        List<Order> orders = new ArrayList<>();
        orders.add(orderForBillF);
        orders.add(orderForBillG);
        return orders;
    }

    public List<Order> ordersForBillT7andT1(){
        List<Order> orders = new ArrayList<>();
        orders.add(orderForBillH);
        orders.add(orderForBillI);
        orders.add(orderForBillJ);
        return orders;
    }
}
