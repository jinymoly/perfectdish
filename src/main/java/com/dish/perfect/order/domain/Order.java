package com.dish.perfect.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.orderItem.domain.OrderItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "table_no")
    private String tableNo;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private BigDecimal totalPrice;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    @Builder
    public Order(Long id, String tableNo, List<OrderItem> orderItems,
            BigDecimal totalPrice, OrderStatus status) {
        this.id = id;
        this.tableNo = tableNo;
        this.orderItems = orderItems;
        this.totalPrice = applyTotalPrice(orderItems);
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("주문 내역:\n");
        for (OrderItem orderItem : orderItems) {
            sb.append(orderItem.getMenu().getMenuName())
                    .append(" (수량: ").append(orderItem.getCount()).append(")\n");
        }
        sb.append("최종 합계: ")
                .append(totalPrice).append("원 [").append(status).append("]");
        return sb.toString();
    }

    public Order(String tableNo) {
        this.tableNo = tableNo;
    }

    public void addOrderItems(OrderItem oItem) {
        if(oItem.getTableNo().equals(tableNo)){
            orderItems.add(oItem);
            oItem.initOrderFrom(this);
        } else {
            throw new GlobalException(ErrorCode.FAIL_CREATE_ORDER, "테이블 번호 오류로 주문을 생성할 수 없습니다.");
        }
    }

    public void updateStatus() {
        this.status = OrderStatus.COMPLETED;
    }

    public void addCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void addCompletedAt(LocalDateTime completedAt){
        this.completedAt = completedAt;
    }

    /**
     * OrderItem별 계산 : 단일 메뉴 
     * 
     * @param price
     * @return
     */
    private BigDecimal calculatePriceByOrderItem(OrderItem orderItem) {
        Menu menu = orderItem.getMenu();
        Integer price = menu.getPrice();

        if (menu.isDiscounted()) {
            price = applyDiscount(price);
        }
        int count = orderItem.getCount();
        int total = price * count;
        return BigDecimal.valueOf(total);
    }

    /**
     * 테이블 별 합계 : 주문서의 메뉴들 
     * 
     * @param orderItems
     * @return
     */
    public BigDecimal applyTotalPrice(List<OrderItem> orderItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(OrderItem orderItem : orderItems){
            BigDecimal orderItemPrice = calculatePriceByOrderItem(orderItem);
            totalPrice = totalPrice.add(orderItemPrice);
        }
        return totalPrice;
    }

    private int applyDiscount(Integer price) {
        return (int) (price * 0.95);
    }

}
