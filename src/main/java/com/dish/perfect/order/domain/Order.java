package com.dish.perfect.order.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Order {

    private Long id;

    private int tableNo;

    private String menuName;
    private int count;
    private Integer price;

    private BigDecimal totalPrice;

    private OrderStatus status;

    // 메뉴리스트에서 메뉴의 이름과 가격만 들고 올 것
    // price의 기준 - 1메뉴? 갯수에 따른 price ?

    @Builder
    public Order(Long id, int tableNo,
            String menuName, int count, Integer price,
            BigDecimal totalPrice, OrderStatus status) {
        this.id = id;
        this.tableNo = tableNo;
        this.menuName = menuName;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.status = status;
    }

}
