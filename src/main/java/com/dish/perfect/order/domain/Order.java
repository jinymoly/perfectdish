package com.dish.perfect.order.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.dish.perfect.bill.domain.Bill;
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
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

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

    @Embedded
    private OrderInfo orderInfo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private Order(String tableNo, Bill bill, OrderInfo orderInfo, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.tableNo = tableNo;
        this.bill = bill;
        this.orderInfo = orderInfo;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public static Order createOrderWithOrderInfo(String tableNo, Bill bill, OrderInfo orderInfo, Menu menu,Integer quantity) {
        Order order = Order.builder()
                .tableNo(tableNo)
                .orderInfo(OrderInfo.of(menu, quantity))
                .orderStatus(OrderStatus.CREATED)
                .build();

        bill.addOrder(order);
        return order;
    }

    @Override
    public String toString() {
        return '\n' + "[id=" + id + "/tableNo." + tableNo + "]" + '\n' +
                "menu=" + orderInfo.getMenu().getMenuName() + "/ quantity=" + orderInfo.getQuantity() + '\n' +
                "status=" + orderStatus + '\n' +
                "createdAt=" + createdAt;
    }

    public void addCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void markOrderStatusAsCompleted(OrderStatus orderStatus) {
        this.orderStatus = OrderStatus.COMPLETED;
    }
}
