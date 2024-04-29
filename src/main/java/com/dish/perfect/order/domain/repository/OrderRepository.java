package com.dish.perfect.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findBytableNo(String tableNo);

    @Query("select o from Order o where o.orderStatus = :orderStatus")
    List<Order> findByOrderStatus(@Param("orderStatus")OrderStatus status);

    /**
      * 테이블 번호가 같고 status가 completed 주문 
      * db에서 집계 먼저
      * @param tableNo
      * @return
      */
    @Query("SELECT COUNT(o.menu) FROM Order o WHERE o.tableNo = :tableNo AND o.orderStatus = :status GROUP BY oi.menu.menuName")
    int countMenuNameWithSameTableNo(@Param("tableNo") String tableNo, @Param("status") OrderStatus status);

    @Query("SELECT o.menu.menuName, SUM(o.count) FROM Order o WHERE o.tableNo = :tableNo AND o.orderStatus = :status GROUP BY o.menu.menuName")
    List<Object[]> countMenuNameWithSameTableNo(@Param("tableNo") String tableNo, @Param("status") OrderStatus status);


    /**
     * 테이블 번호가 같고 status가 completed 주문
     * 리스트로 반환하여 service에서 카운트
     * @param tableNo
     * @param menuName
     * @return
     */
    @Query("SELECT o FROM Order o JOIN o.menu m WHERE o.tableNo = :tableNo AND o.orderStatus = :status")
    List<Order> findByCompletedOrderWithSameTableNo(@Param("tableNo") String tableNo, @Param("status") OrderStatus status);

}
