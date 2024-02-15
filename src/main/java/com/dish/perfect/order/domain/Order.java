package com.dish.perfect.order.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Order {

    private int tableNo;

    private String menuName;
    private boolean isDiscount;
    private int count;
    private Integer price;

    private BigDecimal totalPrice;

    private OrderStatus status;

    // 메뉴리스트에서 메뉴의 이름과 가격만 들고 올 것
    // price의 기준 - 1메뉴? 갯수에 따른 price ?

    @Builder
    public Order(int tableNo,
            String menuName, int count, Integer price,
            BigDecimal totalPrice, OrderStatus status, boolean isDiscount) {
        this.tableNo = tableNo;
        this.menuName = menuName;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.status = status;
        this.isDiscount = isDiscount;
    }

    @Override
    public String toString() {
        return tableNo + "번 테이블 : " + menuName + ", " + price + "원, " + count + ", 총 금액 " + totalPrice
                + "원, [" + status + "]";
    }

    

}
