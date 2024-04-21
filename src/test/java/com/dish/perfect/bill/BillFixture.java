package com.dish.perfect.bill;

import java.util.ArrayList;
import java.util.List;

import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.bill.dto.request.BillRequest;
import com.dish.perfect.order.OrderFixture;
import com.dish.perfect.order.domain.Order;


public class BillFixture {
    
    private OrderFixture orderFixture = new OrderFixture();
    
    private Order orderA = orderFixture.orderRequestA.toOrder();
    private Order orderB = orderFixture.orderRequestB.toOrder();
    private Order orderC = orderFixture.orderRequestC.toOrder();
    private Order orderD = orderFixture.orderRequestD.toOrder();
    private Order orderE = orderFixture.orderRequestE.toOrder();
    private Order orderF = orderFixture.orderRequestF.toOrder();
    private Order orderG = orderFixture.orderRequestG.toOrder();
    private Order orderH = orderFixture.orderRequestH.toOrder();
    private Order orderI = orderFixture.orderRequestI.toOrder();
    private Order orderJ = orderFixture.orderRequestJ.toOrder();
    
    public BillRequest orderRequest1 = BillRequest.builder()
                                                    .tableNo("2")
                                                    .orders(ordersT2())
                                                    .status(BillStatus.NOTSERVED)
                                                    .build();

    public BillRequest orderRequest2 = BillRequest.builder()
                                                    .tableNo("3")
                                                    .orders(ordersT3())
                                                    .status(BillStatus.NOTSERVED)
                                                    .build();
    public BillRequest orderRequest3 = BillRequest.builder()
                                                    .tableNo("7")
                                                    .orders(ordersT7())
                                                    .status(BillStatus.NOTSERVED)
                                                    .build();
    public BillRequest orderRequest3D = BillRequest.builder()
                                                    .tableNo("1")
                                                    .orders(ordersT7andT1())
                                                    .status(BillStatus.NOTSERVED)
                                                    .build();

    
    public List<Order> ordersT2(){
        List<Order> orders = new ArrayList<>();
        orders.add(orderA);
        orders.add(orderE);
        return orders;
    }

    public List<Order> ordersT3(){
        List<Order> orders = new ArrayList<>();
        orders.add(orderB);
        orders.add(orderC);
        orders.add(orderD);
        return orders;
    }

    public List<Order> ordersT7(){
        List<Order> orders = new ArrayList<>();
        orders.add(orderF);
        orders.add(orderG);
        return orders;
    }

    public List<Order> ordersT7andT1(){
        List<Order> orders = new ArrayList<>();
        orders.add(orderH);
        orders.add(orderI);
        orders.add(orderJ);
        return orders;
    }

    
}
