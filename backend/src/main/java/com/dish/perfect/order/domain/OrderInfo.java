package com.dish.perfect.order.domain;

import com.dish.perfect.menu.domain.Menu;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@RequiredArgsConstructor
public class OrderInfo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_name")
    private Menu menu;

    private Integer quantity;

    private OrderInfo(Menu menu, Integer quantity){
        this.menu = menu;
        this.quantity = quantity;
    }

    public static OrderInfo of(Menu menu, Integer quantity){
        return new OrderInfo(menu, quantity);
    }

    public void updateOrderInfoQuantity(Integer quantity){
        this.quantity = quantity;
    }
    
}
