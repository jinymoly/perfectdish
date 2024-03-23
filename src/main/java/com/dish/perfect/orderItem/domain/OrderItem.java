package com.dish.perfect.orderItem.domain;

import java.math.BigDecimal;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "orderitem_id")
    private Long id;

    @Column(nullable = false)
    @OneToMany
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Integer price;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus itemstatus;

    @Builder
    public OrderItem(Long id, Order order, Menu menu,
            int count, BigDecimal totalPrice, OrderItemStatus itemstatus) {
        this.id = id;
        this.order = applyTableNo(order.getTableNo());
        this.menu = applyMenuName(menu.getMenuName());
        this.count = count;
        this.price = menu.getPrice();
        this.totalPrice = createTotalPrice();
        this.itemstatus = itemstatus;
    }

    @Override
    public String toString() {
        return menu.getMenuName() + ", " + price + "원, " + count + "개, total: " + totalPrice
                + "원, [" + itemstatus + "], D: " + menu.isDiscounted();
    }

    private Menu applyMenuName(String menuName) {
        return Menu.builder()
                .menuName(menuName)
                .build();
    }

    private BigDecimal createTotalPrice() {
        if (menu.isDiscounted()) {
            price = applyDiscount(price);
        }
        int total = price * count;
        return new BigDecimal(total);
    }
    
    private int applyDiscount(Integer price) {
        return (int) (price * 0.95);
    }
    
    public Order applyTableNo(String tableNo) {
        return Order.builder()
                .tableNo(tableNo)
                .build();
    }
    
    public void updateStatus() {
        this.itemstatus = OrderItemStatus.COMPLETED;
    }

    public void updateCount(int addCount) {
        this.count += addCount;
    }

    public void applyTotalPrice(BigDecimal addTotalPrice) {
        this.totalPrice = this.totalPrice.add(addTotalPrice);
    }

}
