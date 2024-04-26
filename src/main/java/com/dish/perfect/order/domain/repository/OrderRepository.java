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
      * 테이블 번호가 같고 메뉴이름이 같은 주문 카운트 
      * db에서 집계 먼저
      * @param tableNo
      * @return
      */
    @Query("SELECT COUNT(o.menu) FROM Order o WHERE o.tableNo = :tableNo GROUP BY oi.menu.menuName")
    int countMenuNameWithSameTableNo(@Param("tableNo") String tableNo);

    /**
     * 테이블 번호가 같고 메뉴이름이 같은 주문 카운트 
     * 리스트로 반환하여 service에서 카운트
     * @param tableNo
     * @param menuName
     * @return
     */
    @Query("SELECT o FROM Order o JOIN o.menu m WHERE o.tableNo = :tableNo AND o.orderStatus = :status AND m.menuName = :menuName")
    List<Order> findByCompletedOrderAndSameMenuNameWithSameTableNo(@Param("tableNo") String tableNo, @Param("status") OrderStatus status, @Param("menuName") String menuName);

}
