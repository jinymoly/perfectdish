package com.dish.perfect.orderItem.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
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

    @ElementCollection
    @CollectionTable(name = "orderItem_map", joinColumns = @JoinColumn(name = "orderItem_id"))
    @MapKeyColumn(name = "orderItem_menu")
    @Column(name = "orderItem_count")
    private Map<Menu, Integer> orderItemMap = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_no")
    private Order order;
    
    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderItemStatus;

    private LocalDateTime createdAt;

    @Builder
    public OrderItem(Long id, Map<Menu, Integer> orderItem, Order order, OrderItemStatus orderItemStatus, LocalDateTime createdAt) {
        this.id = id;
        this.orderItemMap = new HashMap<>(orderItem);
        this.order = initOrderFrom(order.getTableNo());
        this.orderItemStatus = orderItemStatus;
        this.createdAt = createdAt;
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
