package com.dish.perfect.bill.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByTableNo(String tableNo);

    @Query("select o from Order o where o.orderStatus = :orderStatus")
    List<Bill> findByOrderStatus(@Param("orderStatus") BillStatus orderStatus);

}
