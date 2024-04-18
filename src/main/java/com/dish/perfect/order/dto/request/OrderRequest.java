package com.dish.perfect.order.dto.request;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequest {

    //private final Menu menu;

    // TO-DO
    // 메뉴 이름으로 메뉴 객체를 생성 하여 Order에 Menu를 초기화 할 때
    // OrderCoreService에서 Order save 시 메뉴에 해당 메뉴 이름을 가진 메뉴가 있는지 확인 후 
    // 있다면 저장 아니라면 예외처리 할 것 
    private final String menuName;
    private final int count;

    private final String tableNo;
    
    private final OrderStatus status;

    @Builder
    private OrderRequest(String menuName, int count, String tableNo, OrderStatus status){
        this.menuName = menuName;
        this.count = count;
        this.tableNo = tableNo;
        this.status = status;
    }

    public Order toOrder(){
        return Order.builder()
                        .tableNo(tableNo)   
                        .menu(new Menu(menuName))
                        .count(count)
                        .tableNo(tableNo)
                        .orderStatus(OrderStatus.CREATED)
                        .build();
    }
}
