package com.dish.perfect.orderItem.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

    @Query("select oi from OrderItem oi where oi.orderItemStatus = :orderItemStatus")
    List<OrderItem> findByOrderItemStatus(@Param("orderItemStatus")OrderItemStatus status);
}
