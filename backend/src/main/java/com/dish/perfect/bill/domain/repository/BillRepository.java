package com.dish.perfect.bill.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByTableNo(String tableNo);

    @Query("select b from Bill b where b.billStatus = :billStatus")
    List<Bill> findByBillStatus(@Param("billStatus") BillStatus billStatus);

    @Modifying(clearAutomatically = true)
    @Query("update Bill b set b.isDeleted = true where b.id = :bill_id")
    void deleteBillById(@Param("bill_id") Long id);

}
