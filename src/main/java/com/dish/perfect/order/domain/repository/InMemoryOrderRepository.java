package com.dish.perfect.order.domain.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.domain.repository.MenuBoardRepository;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.dto.request.OrderRequest;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    @Autowired
    private MenuBoardRepository menuRepository;

    private final List<Order> orders = new ArrayList<>();

    @Override
    public Order createOrder(OrderRequest orderDto) {
        List<Menu> menus = menuRepository.findAllMenus();

        for(Menu menu : menus){
            if(menu.getMenuName() == orderDto.getMenuName()){
                Order order = Order.builder()
                .tableNo(orderDto.getTableNo())
                .menuName(orderDto.getMenuName())
                .price(orderDto.getPrice())
                .count(orderDto.getCount())
                .totalPrice(convertToBigDecimal(getTotalPrice(orderDto.getPrice(), orderDto.getCount())))
                .status(orderDto.getStatus())
                .build();
                orders.add(order);
                return order;
            }
        }
        throw new IllegalStateException("주문할 수 없는 메뉴 입니다.");
    }

    @Override
    public List<Order> getOrders(int tableNo) {
        List<Order> ordersByTableNo = new ArrayList<>();
        for(Order order : orders){
            if(order.getTableNo() == tableNo){
                ordersByTableNo.add(order);
            }
        }
        return ordersByTableNo;
    }

    
    @Override
    public List<Order> getOrderByStatus(OrderStatus status) {
        for(Order order : orders){
            if(order.getStatus() == status){
                return orders;
            }
        }
        return null;
    }
    
    @Override
    public List<Order> addOrderByTableNo(int tableNo, OrderRequest orderDto) {
        for(Order order : orders){
            if(order.getTableNo() == tableNo){
                Order newOrder = Order.builder()
                                        .tableNo(order.getTableNo())
                                        .menuName(orderDto.getMenuName())
                                        .price(orderDto.getPrice())
                                        .count(orderDto.getCount())
                                        .totalPrice(convertToBigDecimal(getTotalPrice(orderDto.getPrice(), orderDto.getCount())))
                                        .build();
                orders.add(newOrder);
            }
        }
        return orders;
    }

    private int getTotalPrice(Integer price, int count){
        int intPrice = price.intValue();
        int total = intPrice * count;
        double vat = total * 0.1;
        return (int) Math.round(total + vat);
    }

    private BigDecimal convertToBigDecimal(int totalPrice){
        return new BigDecimal(totalPrice);
    }
}
