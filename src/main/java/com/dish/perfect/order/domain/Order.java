package com.dish.perfect.order.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dish.perfect.orderItem.domain.OrderItem;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Order {

    private int tableNo;

    private List<OrderItem> orderList = new ArrayList<>();
    private BigDecimal finalPrice;

    private OrderStatus status;

    @Builder
    public Order(int tableNo, List<OrderItem> orderList, BigDecimal finalPrice, OrderStatus status) {
        this.tableNo = tableNo;
        this.orderList = orderList;
        this.finalPrice = BigDecimal.ZERO;
        this.status = OrderStatus.NOTSERVED;
    }

    @Override
    public String toString() {
        return tableNo + "번 테이블" + orderList + "/ 합계: " + finalPrice + "원[" + status + "]";
    }

    public BigDecimal calculateFinalPrice() {
        for (OrderItem order : orderList) {
            if (order.getTableNo() == this.tableNo) {
                finalPrice = finalPrice.add(order.getTotalPrice());
            }
        }
        return finalPrice;
    }

    public void updateStatus() {
        this.status = OrderStatus.COMPLETED;
    }

}
