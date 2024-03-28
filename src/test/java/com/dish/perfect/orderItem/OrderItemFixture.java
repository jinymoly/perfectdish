package com.dish.perfect.orderItem;


import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.orderItem.dto.request.OrderItemRequest;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

public class OrderItemFixture {

    private MenuFixture menuFixture = new MenuFixture();

    private Menu menuA = menuFixture.fixRequestA().toEntity();
    private Menu menuB = menuFixture.fixRequestB().toEntity();
    private Menu menuC = menuFixture.fixRequestC().toEntity();
    private Menu menuD = menuFixture.fixRequestD().toEntity();
    private Menu menuE = menuFixture.fixRequestE().toEntity();
    private Menu menuF = menuFixture.fixRequestF().toEntity();
    private Menu menuG = menuFixture.fixRequestG().toEntity();
    private Menu menuH = menuFixture.fixRequestH().toEntity();

    public OrderItemRequest orderItemRequestA = OrderItemRequest.builder()
                                                                .tableNo("2").menuName(menuA.getMenuName()).count(2)
                                                                .status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestB = OrderItemRequest.builder()
                                                                .tableNo("3").menuName(menuB.getMenuName()).count(1)
                                                                .status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestC = OrderItemRequest.builder()
                                                                .tableNo("3").menuName(menuC.getMenuName()).count(3)
                                                                .status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestD = OrderItemRequest.builder()
                                                                .tableNo("3").menuName(menuD.getMenuName()).count(5)
                                                                .status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestE = OrderItemRequest.builder()
                                                                .tableNo("2").menuName(menuE.getMenuName()).count(2)
                                                                .status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestF = OrderItemRequest.builder()
                                                                .tableNo("7").menuName(menuF.getMenuName()).count(2)
                                                                .status(OrderItemStatus.COMPLETED).build();
    public OrderItemRequest orderItemRequestG = OrderItemRequest.builder()
                                                                .tableNo("7").menuName(menuG.getMenuName()).count(3)
                                                                .status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestH = OrderItemRequest.builder()
                                                                .tableNo("7").menuName(menuH.getMenuName()).count(1)
                                                                .status(OrderItemStatus.COMPLETED).build();


}
