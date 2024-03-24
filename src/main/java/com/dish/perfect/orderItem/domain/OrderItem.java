package com.dish.perfect.orderItem.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @GeneratedValue
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
    private OrderItemStatus itemstatus;

    private LocalDateTime createdAt;

    @Builder
    public OrderItem(Long id, Map<Menu, Integer> orderItem, Order order, OrderItemStatus itemstatus, LocalDateTime createdAt) {
        this.id = id;
        this.orderItemMap = new HashMap<>(orderItem);
        this.order = createOrder(order.getTableNo());
        this.itemstatus = itemstatus;
        this.createdAt = createdAt;
    }

    public Map<Menu, Integer> applyOrderItem(Menu menu, int count){
        Map<Menu, Integer> orderItem =  new HashMap<>();
        orderItem.put(createMenu(menu.getMenuName()), count);
        return orderItem;
    }

    private Menu createMenu(String menuName){
        return new Menu(menuName);
    }
    
    private Order createOrder(String tableNo){
        return new Order(tableNo);
    }
    
    public void addCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    
    public void markOrderItemStatusAsCompleted() {
        this.itemstatus = OrderItemStatus.COMPLETED;
    }
}
