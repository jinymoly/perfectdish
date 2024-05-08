package com.dish.perfect.bill.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.repository.BillRepository;
import com.dish.perfect.bill.dto.request.BillRequest;
import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderInfo;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BillCoreService {

    private final BillRepository billRepository;
    private final OrderRepository orderRepository;

    // TO-DO
    // tableNo가 같아야 orders에 add 할 것
    // order의 status가 COMPLETED일 때만 Bill생성
    // validation
    // 1. orderStatus 가 completed
    // 2. order의 tableNo 일치
    public Bill createBill(BillRequest billRequest) {
        Bill findByTableNo = billRepository.findByTableNo(billRequest.getTableNo());
        findByTableNo.initStatus();
        //areAllOrderStatusCompleted(findByTableNo.getOrders());
        findByTableNo.initTotalPrice(applyTotalPrice(findByTableNo.getOrders()));
        findByTableNo.addCreatedAt(LocalDateTime.now());
        billRepository.save(findByTableNo);
        log.info("created Bill:{}", findByTableNo.getId());
        return findByTableNo;
    }

    /**
     * createOrder시 사용할 Bill
     * @param tableNo
     * @return
     */
    public Bill mergeOrdersAndCreateBillByTableNo(String tableNo){
        Bill findByTableNo = billRepository.findByTableNo(tableNo);
        List<Order> orderListByTableNo = orderRepository.findBytableNo(tableNo);
        if(findByTableNo == null){
            Bill newBill = Bill.builder()
                                .tableNo(tableNo)
                                .orders(orderListByTableNo)
                                .build();
            return billRepository.save(newBill);
        } else {
            return findByTableNo;
        }
    }

    public Bill completeAllOrdersInBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_BILL, "해당 청구서가 존재하지 않습니다."));
        bill.updateStatus();
        bill.addCompletedAt(LocalDateTime.now());
        Bill updatedBill = billRepository.save(bill);
        log.info("{}/{}", updatedBill.getId(), updatedBill.getBillStatus());
        return updatedBill;
    }

    /**
     * 테이블 별 합계 : 주문서의 메뉴들
     * 
     * @param orders
     * @return
     */
    public BigDecimal applyTotalPrice(List<Order> orders) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Order o : orders) {
            OrderInfo orderInfo = o.getOrderInfo();
            Menu menu = orderInfo.getMenu();
            Integer quantity = orderInfo.getQuantity();

            BigDecimal price = calculatePrice(menu);
            BigDecimal totalPriceByMenu = price.multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(totalPriceByMenu);
        }
        return totalPrice;
    }

    /**
     * 단일 메뉴의 isDiscounted flag에 따른 가격
     * 
     * @param price
     * @return
     */
    private BigDecimal calculatePrice(Menu menu) {
        Integer price = menu.getPrice();

        if (menu.isDiscounted()) {
            price = applyDiscount(price);
        }
        return BigDecimal.valueOf(price);
    }

    private int applyDiscount(Integer price) {
        return (int) (price * 0.95);
    }

    /**
     * orders의 모든 주문이 completed되었는지
     * 
     * @param orders
     * @throws GlobalException
     */
    private void areAllOrderStatusCompleted(List<Order> orders) throws GlobalException {
        for (Order o : orders) {
            if (!o.getOrderStatus().equals(OrderStatus.COMPLETED)) {
                throw new GlobalException(ErrorCode.ORDER_NOT_COMPLETED, "모든 음식이 서빙되지 않았습니다.");
            }
        }
    }

}
