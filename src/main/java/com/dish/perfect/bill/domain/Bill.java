package com.dish.perfect.bill.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
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

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private Map<Menu, Integer> orders = new ConcurrentHashMap<>();
    
    //private List<Order> orders = new ArrayList<>();
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private BillStatus billStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime completedAt;

    @Builder
    public Bill(String tableNo, Map<Menu, Integer> orders,
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
        for(Map.Entry<Menu, Integer> menuInfo : orders.entrySet()){
            String menuName = menuInfo.getKey().getMenuName();
            Integer count = menuInfo.getValue();
                menuBuffer.append(menuName +"/ "+ count).append('\n');
        }
        return '\n' + "[tableNo." + tableNo + "]" + '\n' +
                menuBuffer.toString() +
                "totalPrice=" + totalPrice + '\n' +
                "createdAt=" + createdAt + '\n' +
                "completedAt=" + completedAt;
    }

    /**
     * tableNo가 같으면 주문서에 담는다.
     * @param o 
     */
    // public void createOrderListWithTableNoFrom(Order o) {
    //     if(o.getTableNo().equals(tableNo)){
    //         this.orders.add(o);
    //         if(o.getBill() != this){
    //             o.addOrderTo(this);
    //         }
    //     } else {
    //         throw new GlobalException(ErrorCode.FAIL_CREATE_BILL, "테이블 번호 오류로 청구서를 생성할 수 없습니다.");
    //     }
    // }

    public void updateStatus() {
        this.billStatus = BillStatus.COMPLETED;
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
