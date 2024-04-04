package com.dish.perfect.orderItem.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;

    
    @Column(name = "table_no", nullable = false)
    private String tableNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_name")
    private Menu menu;

    private int count;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderItemStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public OrderItem(Long id, String tableNo, Menu menu, Order order, int count, OrderItemStatus orderItemStatus, LocalDateTime createdAt) {
        this.id = id;
        this.tableNo = tableNo;
        this.menu = menu;
        this.order = order;
        this.count = count;
        this.orderItemStatus = orderItemStatus;
        this.createdAt = createdAt;
    }

    @Override
    public String toString(){
        return '\n' + "[id=" + id + "/tableNo."+ tableNo + "]" + '\n' +
        "menu=" + menu + "/ quantity=" + count + '\n' +
        "status=" + orderItemStatus + '\n' +
        "createdAt=" + createdAt;
    }
    
    public void initMenuFrom(Menu menu) {
        this.menu = menu;
    }

    public void initOrder(Order order){
        this.order = order;
        if(!order.getOrderItems().contains(this)){ //무한루프 필터링
            order.getOrderItems().add(this);
        }
    }

    public void addCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void markOrderItemStatusAsCompleted(OrderItemStatus orderItemStatus) {
        this.orderItemStatus = OrderItemStatus.COMPLETED;
    }
}
