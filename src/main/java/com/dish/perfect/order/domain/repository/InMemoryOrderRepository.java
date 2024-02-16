package com.dish.perfect.order.domain.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.domain.repository.MenuBoardRepository;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.dto.request.OrderRequest;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class InMemoryOrderRepository implements OrderRepository {

    @Autowired
    private MenuBoardRepository menuRepository;

    private final List<Order> orders = new ArrayList<>();

    @Override
    public Order createOrder(OrderRequest orderDto) {
        String menuName = orderDto.getMenu().getMenuName();
        Menu menu = menuRepository.getMenuByName(menuName);
        if (menu != null) {
            Order order = Order.builder()
                    .tableNo(orderDto.getTableNo())
                    .menuName(orderDto.getMenu().getMenuName())
                    .price(orderDto.getMenu().getPrice())
                    .count(orderDto.getCount())
                    .status(orderDto.getStatus())
                    .isDiscount(orderDto.getMenu().isDiscounted())
                    .build();
            orders.add(order);
            log.info("createOrder() {}", order);

            return order;
        }
        throw new GlobalException(ErrorCode.FAIL_CREATE_ORDER, "주문할 수 없는 메뉴입니다.");
    }

    @Override
    public List<Order> getOrders(int tableNo) {
        List<Order> ordersByTableNo = new ArrayList<>();
        for (Order order : orders) {
            if (order.getTableNo() == tableNo) {
                ordersByTableNo.add(order);
            }
        }
        log.info("getOrders() {}", ordersByTableNo);

        return ordersByTableNo;
    }

    @Override
    public List<Order> getOrderByStatus(OrderStatus status) {
        for (Order order : orders) {
            if (order.getStatus() == status) {
                return orders;
            }
        }
        log.info("getOrderByStatus() {}", orders);

        throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 주문 목록이 없습니다.");
    }

    public void clear() {
        orders.clear();
    }

    @Override
    public List<Order> getAllOrders() {
        if(!orders.isEmpty()){
            return orders;
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER,"주문 내역이 없습니다.");
        }
    }
}
