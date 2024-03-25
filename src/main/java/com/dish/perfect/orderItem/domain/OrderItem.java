package com.dish.perfect.orderItem.domain;

import java.time.LocalDateTime;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    private Menu menu;

    private int count;
    
    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderItemStatus;

    private LocalDateTime createdAt;

    @Builder
    public OrderItem(Long id, Menu menu, int count, Order order, OrderItemStatus orderItemStatus, LocalDateTime createdAt) {
        this.id = id;
        this.order = order;
        this.count = count;
        this.menu = initMenuFrom(menu.getMenuName());
        this.orderItemStatus = orderItemStatus;
        this.createdAt = createdAt;
    }

    private Menu initMenuFrom(String menuName){
        return new Menu(menuName);
    }

    private Order initOrderFrom(String tableNo){
        return new Order(tableNo);
    }
    
    public void addCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    
    public void markOrderItemStatusAsCompleted() {
        this.orderItemStatus = OrderItemStatus.COMPLETED;
    }
}
