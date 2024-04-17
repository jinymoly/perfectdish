package com.dish.perfect.order;


import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Menu;
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
                                                                .count(2)
                                                                .status(OrderStatus.CREATED)
                                                                .build();

    public OrderRequest orderRequestB = OrderRequest.builder()
                                                                .tableNo("3")
                                                                .menuName(menuB.getMenuName())
                                                                .count(1)
                                                                .status(OrderStatus.CREATED)
                                                                .build();

    public OrderRequest orderRequestC = OrderRequest.builder()
                                                                .tableNo("3")
                                                                .menuName(menuC.getMenuName())
                                                                .count(3)
                                                                .status(OrderStatus.CREATED)
                                                                .build();

    public OrderRequest orderRequestD = OrderRequest.builder()
                                                                .tableNo("3")
                                                                .menuName(menuD.getMenuName())
                                                                .count(5)
                                                                .status(OrderStatus.CREATED)
                                                                .build();

    public OrderRequest orderRequestE = OrderRequest.builder()
                                                                .tableNo("2")
                                                                .menuName(menuE.getMenuName())
                                                                .count(2)
                                                                .status(OrderStatus.CREATED)
                                                                .build();

    public OrderRequest orderRequestF = OrderRequest.builder()
                                                                .tableNo("7")
                                                                .menuName(menuF.getMenuName())
                                                                .count(2)
                                                                .status(OrderStatus.COMPLETED)
                                                                .build();

    public OrderRequest orderRequestG = OrderRequest.builder()
                                                                .tableNo("7")
                                                                .menuName(menuG.getMenuName())
                                                                .count(3)
                                                                .status(OrderStatus.CREATED)
                                                                .build();

    public OrderRequest orderRequestH = OrderRequest.builder()
                                                                .tableNo("7")
                                                                .menuName(menuH.getMenuName())
                                                                .count(1)
                                                                .status(OrderStatus.COMPLETED)
                                                                .build();

    public OrderRequest orderRequestI = OrderRequest.builder()
                                                                .tableNo("1")
                                                                .menuName(menuH.getMenuName())
                                                                .count(1)
                                                                .status(OrderStatus.COMPLETED)
                                                                .build();

    public OrderRequest orderRequestJ = OrderRequest.builder()
                                                                .tableNo("1")
                                                                .menuName(menuB.getMenuName())
                                                                .count(3)
                                                                .status(OrderStatus.CREATED)
                                                                .build();



}
