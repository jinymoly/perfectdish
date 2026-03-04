package com.dish.perfect.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findBytableNo(String tableNo);

    List<Order> findByPhoneNumber(String phoneNumber);

    @Query("select o from Order o join o.bill b where b.tableNo = :tableNo")
    List<Order> findByTableNoWithBill(@Param("tableNo") String tableNo);

    @Query("select o from Order o where o.orderStatus = :orderStatus")
    List<Order> findByOrderStatus(@Param("orderStatus")OrderStatus orderStatus);

}
