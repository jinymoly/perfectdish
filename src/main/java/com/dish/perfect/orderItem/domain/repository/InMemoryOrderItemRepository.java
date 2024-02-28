package com.dish.perfect.orderItem.domain.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.domain.repository.MenuBoardRepository;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;
import com.dish.perfect.orderItem.dto.request.OrderItemRequest;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class InMemoryOrderItemRepository implements OrderItemRepository {

    @Autowired
    private MenuBoardRepository menuRepository;

    private final List<OrderItem> orders = new ArrayList<>();

    @Override
    public OrderItem createOrder(OrderItemRequest orderDto) {
        String menuName = orderDto.getMenu().getMenuName();
        Menu menu = menuRepository.getMenuByName(menuName);
        if (menu != null) {
            OrderItem order = OrderItem.builder()
                    .tableNo(orderDto.getTableNo())
                    .menuName(orderDto.getMenu().getMenuName())
                    .price(orderDto.getMenu().getPrice())
                    .count(orderDto.getCount())
                    .itemstatus(orderDto.getStatus())
                    .isDiscounted(orderDto.getMenu().isDiscounted())
                    .build();
            orders.add(order);
            log.info("createOrder() {}", order);

            return order;
        }
        throw new GlobalException(ErrorCode.FAIL_CREATE_ORDER, "주문할 수 없는 메뉴입니다.");
    }

    @Override
    public List<OrderItem> getOrders(int tableNo) {
        List<OrderItem> ordersByTableNo = new ArrayList<>();
        for (OrderItem order : orders) {
            if (order.getTableNo() == tableNo) {
                ordersByTableNo.add(order);
            }
        }
        log.info("getOrders() {}", ordersByTableNo);

        return ordersByTableNo;
    }

    @Override
    public List<OrderItem> getOrderByStatus(OrderItemStatus status) {
        for (OrderItem order : orders) {
            if (order.getItemstatus() == status) {
                return orders;
            }
        }
        log.info("getOrderByStatus() {}", orders);

        throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 주문 목록이 없습니다.");
    }

    @Override
    public List<OrderItem> getAllOrders() {
        if (!orders.isEmpty()) {
            return orders;
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "주문 내역이 없습니다.");
        }
    }
    @Override
    public void clear() {
        orders.clear();
    }
}
