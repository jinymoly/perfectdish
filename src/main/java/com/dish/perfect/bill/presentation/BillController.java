package com.dish.perfect.bill.presentation;

import org.springframework.web.bind.annotation.RestController;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.dto.request.BillRequest;
import com.dish.perfect.bill.dto.response.BillResponse;
import com.dish.perfect.bill.service.BillCoreService;
import com.dish.perfect.bill.service.BillPresentationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("bill")
public class BillController {
    
    private final BillCoreService billCoreService;
    private final BillPresentationService billPresentationService;

    @GetMapping("/create")
    public String addBillRequest(){
        return "BillRequest 페이지로 이동";
    }

    @PostMapping("/create")
    public ResponseEntity<Bill> createOrder(@RequestBody @Valid BillRequest billRequest){
        Bill newOrder = billCoreService.createBill(billRequest);
        return ResponseEntity.ok(newOrder);
    }

    /**
     * 해당 테이블 최종 영수증 
     * @param tableNo
     * @return
     */
    @GetMapping("/{tableNo}")
    public ResponseEntity<BillResponse> getBillByTableNo(@PathVariable("tableNo") String tableNo) {
        BillResponse bill = billPresentationService.findBillByTableNo(tableNo);
        return ResponseEntity.ok(bill);
    }

    /**
     * 해당 테이블 상태 -> 서빙 완료
     * @param id
     * @return
     */
    @PatchMapping("/{id}/editStatus")
    public ResponseEntity<Void> completeBillStatus(@PathVariable("id") Long id){
        Bill bill = billPresentationService.findBillById(id);
        Bill completeAllOrdersInBill = billCoreService.completeAllOrdersInBill(bill.getId());
        log.info("Bill {} status updated to: {}", completeAllOrdersInBill.getTableNo(), completeAllOrdersInBill.getBillStatus());
        return ResponseEntity.ok().build();
    }
}
