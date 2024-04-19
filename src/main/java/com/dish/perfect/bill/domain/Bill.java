package com.dish.perfect.bill.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<Order> orders = new ArrayList<>();

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private BillStatus orderStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime completedAt;

    @Builder
    public Bill(Long id, String tableNo, List<Order> orders,
            BigDecimal totalPrice, BillStatus orderStatus, LocalDateTime createdAt, LocalDateTime completedAt) {
        this.id = id;
        this.tableNo = tableNo;
        this.orders = orders;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    /**
     * tableNo가 같으면 주문서에 담는다.
     * @param o
     */
    public void initOrderFrom(Order o) {
        if(o.getTableNo().equals(tableNo)){
            this.orders.add(o);
            if(o.getBill() != this){ // 무한루프 필터링
                o.addOrderTo(this);
            }
        } else {
            throw new GlobalException(ErrorCode.FAIL_CREATE_ORDER, "테이블 번호 오류로 주문을 생성할 수 없습니다.");
        }
    }

    public void updateStatus() {
        this.orderStatus = BillStatus.COMPLETED;
    }

    public void addCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void addCompletedAt(LocalDateTime completedAt){
        this.completedAt = completedAt;
    }

    /**
     * Order별 계산 : 단일 메뉴 * count
     * 
     * @param price
     * @return
     */
    private BigDecimal calculatePriceByOrder(Order order) {
        Menu menu = order.getMenu();
        Integer price = menu.getPrice();

        if (menu.isDiscounted()) {
            price = applyDiscount(price);
        }
        int count = order.getCount();
        int total = price * count;
        return BigDecimal.valueOf(total);
    }

    /**
     * 테이블 별 합계 : 주문서의 메뉴들 
     * 
     * @param orders
     * @return
     */
    public BigDecimal applyTotalPrice(List<Order> orders) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(Order order : orders){
            BigDecimal orderPrice = calculatePriceByOrder(order);
            totalPrice = totalPrice.add(orderPrice);
        }
        return totalPrice;
    }

    private int applyDiscount(Integer price) {
        return (int) (price * 0.95);
    }

}
