package com.dish.perfect.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.orderItem.domain.OrderItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "table_no", nullable = false)
    private String tableNo;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime completedAt;

    @Builder
    public Order(Long id, String tableNo, List<OrderItem> orderItems,
            BigDecimal totalPrice, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime completedAt) {
        this.id = id;
        this.tableNo = tableNo;
        this.orderItems = orderItems;
        this.totalPrice = applyTotalPrice(orderItems);
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    /**
     * tableNo가 같으면 주문서에 담는다.
     * @param oItem
     */
    public void addOrderItem(OrderItem oItem) {
        if(oItem.getTableNo().equals(tableNo)){
            this.orderItems.add(oItem);
            if(oItem.getOrder() != this){ // 무한루프 필터링
                oItem.initOrder(this);
            }
        } else {
            throw new GlobalException(ErrorCode.FAIL_CREATE_ORDER, "테이블 번호 오류로 주문을 생성할 수 없습니다.");
        }
    }

    public void updateStatus() {
        this.orderStatus = OrderStatus.COMPLETED;
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
