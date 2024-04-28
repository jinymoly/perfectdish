package com.dish.perfect.bill.dto.request;

import com.dish.perfect.bill.domain.BillStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BillRequest {

    private final String tableNo;
    private final BillStatus status;

    @Builder
    private BillRequest(String tableNo, BillStatus status) {
        this.tableNo = tableNo;
        this.status = status;
    }
}
