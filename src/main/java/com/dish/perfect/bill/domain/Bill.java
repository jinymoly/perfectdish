package com.dish.perfect.bill.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.dish.perfect.order.domain.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long id;

    @Column(name = "table_no", nullable = false)
    private String tableNo;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();
    
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private BillStatus billStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime completedAt;

    @Builder
    public Bill(String tableNo, List<Order> orders,
            BigDecimal totalPrice, BillStatus billStatus, LocalDateTime createdAt, LocalDateTime completedAt) {
        this.tableNo = tableNo;
        this.orders = orders;
        this.totalPrice = totalPrice;
        this.billStatus = billStatus;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        StringBuffer menuBuffer = new StringBuffer();
        for(Order order : orders){
            String menuName = order.getOrderInfo().getMenu().getMenuName();
                menuBuffer.append(menuName).append('\n');
        }
        return '\n' + "[tableNo." + tableNo + "]" + '\n' +
                menuBuffer.toString() +
                "totalPrice=" + totalPrice + '\n' +
                "createdAt=" + createdAt + '\n' +
                "completedAt=" + completedAt;
    }

    public static Bill toBill(String tableNo, List<Order> orders, BillStatus status){
        return Bill.builder()
            .tableNo(tableNo)
            .orders(orders)
            .billStatus(status)
            .build();
    }

    public void initStatus(){
        this.billStatus = BillStatus.NOTSERVED;
    }

    public void updateStatus() {
        this.billStatus = BillStatus.COMPLETED;
    }

    /**
     * 테이블 번호가 같으면 bill의 orders에 추가 됨
     * @param order
     */
    public void addOrderToList(Order order){
        if(isSameTableNo(order.getTableNo())){
            orders.add(order);
            if(order.getBill() != this){
                order.addBill(this);
            }
        }
    }

    /**
     * bill의 tableNo와 input가 일치
     * @param inputTableNo
     * @return
     */
    boolean isSameTableNo(String inputTableNo){
        return tableNo.equals(inputTableNo);
    }

    public void initTotalPrice(BigDecimal totalPrice){
        this.totalPrice = totalPrice;
    }

    public void addCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void addCompletedAt(LocalDateTime completedAt){
        this.completedAt = completedAt;
    }

}
