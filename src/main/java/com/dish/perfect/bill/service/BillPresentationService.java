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

    private final BillRepository billRepository;

    public BillResponse getBillInfo(final Long id){
        Bill bill = billRepository.findById(id).orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_BILL, "해당 청구서가 존재하지 않습니다."));
        return BillResponse.fromBillResponse(bill);
    }

    public List<BillResponse> findAll(){
        return billRepository.findAll()
                                .stream()
                                .map(BillResponse::fromBillResponse)
                                .toList();
                                
    }

    /**
     * 테이블 별 영수증
     * @param tableNo
     * @return
     */
    public BillResponse findBillByTableNo(String tableNo){
        Bill bill = billRepository.findByTableNo(tableNo);
        if(bill != null){
            return BillResponse.fromBillResponse(bill);
        }
        throw new GlobalException(ErrorCode.NOT_FOUND_BILL_BY_TABLE, "해당 테이블의 영수증이 존재하지 않습니다.");
    }

    /**
     * 아직 서빙 되지 않은 모든 주문
     * @param orderStatus
     * @return
     */
    public List<BillResponse> findBillByBillStatus(BillStatus billStatus){
        List<Bill> orders = billRepository.findByBillStatus(billStatus);
        if(!orders.isEmpty()){
            return orders.stream()
                        .filter(order -> order.getBillStatus().equals(BillStatus.NOTSERVED))
                        .map(BillResponse::fromBillResponse)
                        .toList();
        } else {
            throw new GlobalException(ErrorCode.ALREADY_COMPLETED_BILL, "모든 음식이 제공되었습니다.");
        }
    }

}
