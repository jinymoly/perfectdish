package com.dish.perfect.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findBytableNo(String tableNo);

    @Query("select oi from Order oi where oi.orderStatus = :orderStatus")
    List<Order> findByOrderStatus(@Param("orderStatus")OrderStatus status);
}
