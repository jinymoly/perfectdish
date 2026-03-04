package com.dish.perfect.bill.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;
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

    /**
     * 첫 bill 생성 
     * @param billRequest
     * @return
     */
    public Bill createBill(BillRequest billRequest) {
        List<Bill> bills = billRepository.findByTableNo(billRequest.getTableNo());
        if (bills.isEmpty()) {
            throw new GlobalException(ErrorCode.NOT_FOUND_BILL_BY_TABLE, "해당 테이블의 영수증이 존재하지 않습니다.");
        }
        Bill findByTableNo = bills.get(bills.size() - 1); // Get latest
        findByTableNo.initStatus();
        findByTableNo.initTotalPrice(applyTotalPrice(findByTableNo.getOrders()));
        billRepository.save(findByTableNo);
        log.info("created Bill:{}", findByTableNo.getId());
        return findByTableNo;
    }

    /**
     * createOrder시 사용할 Bill 반환(bill이 없으면 생성)
     * 주문서 병합
     * @param tableNo
     * @return
     */
    public Bill mergeOrdersAndCreateBillByTableNo(String tableNo){
        List<Bill> bills = billRepository.findByTableNo(tableNo);
        List<Order> orderListByTableNo = orderRepository.findBytableNo(tableNo);
        if(bills.isEmpty()){
            Bill newBill = Bill.builder()
                                .tableNo(tableNo)
                                .orders(orderListByTableNo)
                                .build();
            return billRepository.save(newBill);
        } else {
            // Find latest non-completed bill if possible, otherwise just latest
            return bills.get(bills.size() - 1);
        }
    }

    public Bill createNewBill(String tableNo) {
        Bill newBill = Bill.builder()
                            .tableNo(tableNo)
                            .orders(new java.util.ArrayList<>())
                            .build();
        return billRepository.save(newBill);
    }

    /**
     * billStatus COMPLETED
     * @param id
     * @return
     */
    public Bill completeAllOrdersInBill(Long id) {
        Bill bill = findBillByIdExceptOptional(id);
        for(Order order : bill.getOrders()){
            if(!order.getOrderStatus().equals(OrderStatus.COMPLETED)){
                order.markOrderStatusAsCompleted(OrderStatus.COMPLETED);
                order.addModifiedAt();
                orderRepository.save(order);
            }
        }
        bill.updateStatus();
        bill.updateLastModifiedAt();
        Bill updatedBill = billRepository.save(bill);
        log.info("{}/{}", updatedBill.getId(), updatedBill.getBillStatus());
        return updatedBill;
    }

    /**
     * update Bill
     * order 추가 시 bill에 order 추가 
     * TO-DO
     * bill과 
     * @param bill
     * @return
     */
    public Bill updateBillWhenAddOrder(String tableNo){
        List<Bill> bills = billRepository.findByTableNo(tableNo);
        if (bills.isEmpty()) {
             throw new GlobalException(ErrorCode.NOT_FOUND_BILL_BY_TABLE, "해당 테이블의 영수증이 존재하지 않습니다.");
        }
        Bill bill = bills.get(bills.size() - 1);
        bill.initTotalPrice(applyTotalPrice(bill.getOrders()));
        billRepository.save(bill);
        log.info("bill has been updated due to order update. bill tableNo:{}/modifiedAt:{}", bill.getTableNo(), bill.getLastModifiedAt());

        return bill;
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

    // orders가 모두 COMPLETED 여야 한다.
    // BillStatus가 COMPLETED 여야 한다. 
    // isPaid가 true 여야 한다. 
    /**
     * Bill soft delete
     * [조건]
     * orders completed
     * billStatus completed
     * isPaid true 
     * @param id
     */
    public void deletedBillByIsPaid(Long id){
        areAllOrderStatusCompleted(id);
        isBillStatusCompleted(id);
        validateIsPaid(id);
        billRepository.deleteBillById(id);

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
    private void areAllOrderStatusCompleted(Long id) throws GlobalException {
        Bill bill = findBillByIdExceptOptional(id);
        for (Order o : bill.getOrders()) {
            if (!o.getOrderStatus().equals(OrderStatus.COMPLETED)) {
                throw new GlobalException(ErrorCode.ORDER_NOT_COMPLETED, "모든 음식이 서빙되지 않았습니다.");
            }
        }
    }

    /**
     * billStatus가 COMPLETED인지
     * @param id
     */
    private void isBillStatusCompleted(Long id){
       Bill bill = findBillByIdExceptOptional(id);
       if(!bill.getBillStatus().equals(BillStatus.COMPLETED)){
            new GlobalException(ErrorCode.ALREADY_COMPLETED_BILL, "아직 제공되지 않은 음식이 존재합니다.");
        }
    }

    /**
     * isPaid가 true
     * @param id
     */
    private void validateIsPaid(Long id){
        Bill bill = findBillByIdExceptOptional(id);
        if(!bill.isPaid()){
           throw new GlobalException(ErrorCode.FAIL_DELETED_BILL, "해당 bill을 삭제할 수 없습니다.");
        }
    }

    /**
     * optional이 아닌 bill 객체를 반환
     * @param id
     * @return
     */
    public Bill findBillByIdExceptOptional(Long id){
        return billRepository.findById(id)
        .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_BILL, "해당 Bill이 존재하지 않습니다."));

    }

}
