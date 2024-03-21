package com.dish.perfect.orderItem.domain;

import java.math.BigDecimal;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "orderitem_id")
    private Long id;

    @OneToMany
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_no")
    private Order order;

    private int count;
    private Integer price;
    
    private BigDecimal totalPrice;
    
    private OrderItemStatus itemstatus;

    @Builder
    public OrderItem(Long id,
                    Order order,
                    Menu menu, 
                    int count,
                    BigDecimal totalPrice, 
                    OrderItemStatus itemstatus) {
        this.id = id;
        this.order = applyOrderTableNo(order.getTableNo());
        this.menu = menu;
        this.count = count;
        this.price = menu.getPrice();
        this.totalPrice = applyTotalPrice();
        this.itemstatus = itemstatus;
    }

    @Override
    public String toString() {
        return menu.getMenuName() + ", " + price + "원, " + count + "개, total: " + totalPrice
                + "원, [" + itemstatus + "], D: " + menu.isDiscounted();
    }

    public BigDecimal applyTotalPrice(){
        if(menu.isDiscounted()){
           price = applyDiscount(price);
        }
        int total = price * count;
        return new BigDecimal(total);
    }

    public Order applyOrderTableNo(String tableNo){
        return Order.builder()
                    .tableNo(tableNo)
                    .build();
    }

    public int applyDiscount(Integer price){
        return (int) (price * 0.95);
    }

    public void updateStatus(){
        this.itemstatus = OrderItemStatus.COMPLETED;
    }

    public void updateCount(int count){
        this.count = count;
    }

    public void applyCount(int addCount){
        this.count += addCount;
    }

    public void applyTotalPrice(BigDecimal addTotalPrice){
        this.totalPrice = this.totalPrice.add(addTotalPrice);
    }

}
