package com.dish.perfect.orderItem.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItem {

    private int tableNo;

    private String menuName;
    private int count;
    private Integer price;
    
    private boolean isDiscount;
    private BigDecimal totalPrice;
    
    private OrderItemStatus itemstatus;

    @Builder
    public OrderItem(int tableNo,
            String menuName, int count, Integer price,
            BigDecimal totalPrice, OrderItemStatus itemstatus, boolean isDiscount) {
        this.tableNo = tableNo;
        this.menuName = menuName;
        this.count = count;
        this.price = price;
        this.totalPrice = addTotalPrice();
        this.itemstatus = itemstatus;
        this.isDiscount = isDiscount;
    }

    @Override
    public String toString() {
        return menuName + ", " + price + "원, " + count + "개, total: " + totalPrice
                + "원, [" + itemstatus + "], D: " + isDiscount;
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

    public void updateStatus(){
        this.itemstatus = OrderItemStatus.COMPLETED;
    }

    public void updateCount(int count){
        this.count = count;
    }

}
