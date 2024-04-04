package com.dish.perfect.orderItem.dto.request;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemRequest {

    //private final Menu menu;

    // TO-DO
    // 메뉴 이름으로 메뉴 객체를 생성 하여 OrderItem에 Menu를 초기화 할 때
    // OrderItemCoreService에서 OrderItem save 시 메뉴에 해당 메뉴 이름을 가진 메뉴가 있는지 확인 후 
    // 있다면 저장 아니라면 예외처리 할 것 
    private final String menuName;
    private final int count;

    private final String tableNo;
    
    private final OrderItemStatus status;

    @Builder
    private OrderItemRequest(String menuName, int count, String tableNo, OrderItemStatus status){
        this.menuName = menuName;
        this.count = count;
        this.tableNo = tableNo;
        this.status = status;
    }

    public OrderItem toEntity(){
        return OrderItem.builder()
                        .tableNo(tableNo)   
                        .menu(new Menu(menuName))
                        .count(count)
                        .tableNo(tableNo)
                        // order(new Order(tableNo)) ㅈㅔ거 
                        .orderItemStatus(OrderItemStatus.CREATED)
                        .build();
    }
}
