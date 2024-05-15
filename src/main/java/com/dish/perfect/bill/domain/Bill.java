package com.dish.perfect.bill.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dish.perfect.global.base.BaseTimeEntity;
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
public class Bill extends BaseTimeEntity{

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

    @Builder
    public Bill(String tableNo, List<Order> orders,
            BigDecimal totalPrice, BillStatus billStatus) {
        this.tableNo = tableNo;
        this.orders = orders;
        this.totalPrice = totalPrice;
        this.billStatus = billStatus;
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
                "createdAt=" + getCreatedAt() + '\n' +
                "completedAt=" + getLastModifiedAt();
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
        updateLastModifiedAt();
    }

    public void addModifiedAt() {
        updateLastModifiedAt();
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

}
