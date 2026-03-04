package com.dish.perfect.order.domain;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.global.base.BaseTimeEntity;
import com.dish.perfect.menu.domain.Menu;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @Column(name = "table_no", nullable = false)
    private String tableNo;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private OrderInfo orderInfo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @PrePersist
    protected void prePersist() {
        if (bill != null) {
            java.time.LocalDateTime updatedAt = getLastModifiedAt() != null ? getLastModifiedAt() : java.time.LocalDateTime.now();
            bill.updateLastOrderUpdatedAt(updatedAt);
        }
    }

    @PreUpdate
    protected void preUpdate() {
        if (bill != null) {
            java.time.LocalDateTime updatedAt = getLastModifiedAt() != null ? getLastModifiedAt() : java.time.LocalDateTime.now();
            bill.updateLastOrderUpdatedAt(updatedAt);
        }
    }

    @Builder
    private Order(String tableNo, String phoneNumber, Bill bill, OrderInfo orderInfo, OrderStatus orderStatus) {
        this.tableNo = tableNo;
        this.phoneNumber = phoneNumber;
        this.bill = bill;
        this.orderInfo = orderInfo;
        this.orderStatus = orderStatus;
    }

    public static Order createOrderWithOrderInfo(String tableNo, String phoneNumber, Menu menu, Integer quantity) {
        Order order = Order.builder()
                .tableNo(tableNo)
                .phoneNumber(phoneNumber)
                .orderInfo(OrderInfo.of(menu, quantity))
                .orderStatus(OrderStatus.CREATED)
                .build();
        return order;
    }

    @Override
    public String toString() {
        return '\n' + "[id=" + id + "/tableNo." + tableNo + "]" + '\n' +
                "menu=" + orderInfo.getMenu().getMenuName() + "/ quantity=" + orderInfo.getQuantity() + '\n' +
                "status=" + orderStatus + '\n' +
                "createdAt=" + getCreatedAt() +
                "modifiedAt=" + getLastModifiedAt();
    }

    /**
     * 같은 메뉴가 추가될 때 주문 일시 수정
     */
    public void addModifiedAt() {
        updateLastModifiedAt();
    }

    public void markOrderStatusAsCompleted(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addBill(Bill newBill) {
        this.bill = newBill;
    }

}
