package com.dish.perfect.order.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    private List<Order> orderList;
    private BigDecimal finalPrice;

    private Map<Integer, List<Order>> orderMap;

    @Builder
    public Order(int tableNo,
            String menuName, int count, Integer price,
            BigDecimal totalPrice, OrderStatus status, boolean isDiscount) {
        this.tableNo = tableNo;
        this.menuName = menuName;
        this.count = count;
        this.price = price;
        this.totalPrice = addTotalPrice();
        this.status = status;
        this.isDiscount = isDiscount;
    }

    @Override
    public String toString() {
        return "tableNo." + tableNo + " : " + menuName + ", " + price + "원, " + count + ", 총 금액 " + totalPrice
                + "원, [" + status + "], D: " + isDiscount;
    }

    public BigDecimal addTotalPrice(){
        if(isDiscount){
           price = applyDiscount(price);
        }
        int total = price * count;
        return new BigDecimal(total);
    }

    public int applyDiscount(Integer price){
        return (int) (price * 0.95);
    }

    public BigDecimal calculateFinalPrice(){
        BigDecimal finalPrice = BigDecimal.ZERO;
        for(Order order : orderList){
            finalPrice = finalPrice.add(order.getTotalPrice());
        }
        return finalPrice;
    }

}
