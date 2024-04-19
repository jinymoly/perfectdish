package com.dish.perfect.bill.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.bill.domain.repository.BillRepository;
import com.dish.perfect.bill.dto.response.BillResponse;
import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BillPresentationService {

    private final BillRepository orderRepository;

    /**
     * 단일 order 정보 반환 
     * @param id
     * @return
     */
    public BillResponse getOrderInfo(final Long id){
        Bill order = orderRepository.findById(id).orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 주문이 존재하지 않습니다."));
        return BillResponse.fromOrderResponse(order);
    }

    public List<BillResponse> findAll(){
        return orderRepository.findAll()
                                .stream()
                                .map(BillResponse::fromOrderResponse)
                                .toList();
                                
    }

    /**
     * 테이블 별 모든 주문 내역
     * @param tableNo
     * @return
     */
    public List<BillResponse> findOrderByTableNo(String tableNo){
        List<Bill> orders = orderRepository.findByTableNo(tableNo);
        if(!orders.isEmpty()){
            return orders.stream()
                        .map(BillResponse::fromOrderResponse)
                        .toList();
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "해당 테이블에 주문 내역이 존재하지 않습니다.");
        }
    }

    /**
     * 아직 서빙 되지 않은 모든 주문 내역
     * @param orderStatus
     * @return
     */
    public List<BillResponse> findOrderByOrderStatus(BillStatus orderStatus){
        List<Bill> orders = orderRepository.findByOrderStatus(orderStatus);
        if(!orders.isEmpty()){
            return orders.stream()
                        .filter(order -> order.getOrderStatus().equals(BillStatus.NOTSERVED))
                        .map(BillResponse::fromOrderResponse)
                        .toList();
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_ORDER, "모든 음식이 서빙되었습니다.");
        }
    }
}
