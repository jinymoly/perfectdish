package com.dish.perfect.order.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByTableNo(String tableNo);

    @Query("select o from Order o where o.orderStatus = :orderStatus")
    List<Order> findByOrderStatus(@Param("orderStatus") OrderStatus orderStatus);

}
