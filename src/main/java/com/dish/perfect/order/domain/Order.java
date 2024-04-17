package com.dish.perfect.order.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.menu.domain.Menu;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_name")
    private Menu menu;

    private int count;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Order(Long id, String tableNo, Menu menu, Bill bill, int count, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.id = id;
        this.tableNo = tableNo;
        this.menu = menu;
        this.bill = bill;
        this.count = count;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    @Override
    public String toString(){
        return '\n' + "[id=" + id + "/tableNo."+ tableNo + "]" + '\n' +
        "menu=" + menu + "/ quantity=" + count + '\n' +
        "status=" + orderStatus + '\n' +
        "createdAt=" + createdAt;
    }
    
    public void initMenuFrom(Menu menu) {
        this.menu = menu;
    }

    public void initOrder(Bill bill){
        this.bill = bill;
        if(!bill.getOrders().contains(this)){ //무한루프 필터링
            bill.getOrders().add(this);
        }
    }

    public void addCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void markOrderStatusAsCompleted(OrderStatus orderStatus) {
        this.orderStatus = OrderStatus.COMPLETED;
    }
}
