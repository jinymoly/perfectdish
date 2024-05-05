package com.dish.perfect.bill;

import com.dish.perfect.bill.domain.Bill;
import com.dish.perfect.bill.domain.BillStatus;
import com.dish.perfect.bill.dto.request.BillRequest;
import com.dish.perfect.order.OrderFixture;

public class BillFixture {
    
    private OrderFixture orderFixture = new OrderFixture();

    Bill billT2 = Bill.builder()
                    .tableNo("3")
                    .orders(orderFixture.ordersForBillT2())
                    .build();
    

    Bill billT3 = Bill.builder()
                    .tableNo("3")
                    .orders(orderFixture.ordersForBillT3())
                    .build();

    Bill billT7 = Bill.builder()
                    .tableNo("3")
                    .orders(orderFixture.ordersForBillT7())
                    .build();
    
    public BillRequest orderRequestTableNo2 = BillRequest.builder()
                                                    .tableNo("2")
                                                    .orders(orderFixture.ordersForBillT2())
                                                    .billStatus(BillStatus.NOTSERVED)
                                                    .build();
    
    public BillRequest orderRequestTableNo2A = BillRequest.builder()
                                                    .tableNo("2")
                                                    .orders(orderFixture.ordersForBillT2A())
                                                    .billStatus(BillStatus.NOTSERVED)
                                                    .build();
    public BillRequest orderRequestTableNo3 = BillRequest.builder()
                                                    .tableNo("3")
                                                    .orders(orderFixture.ordersForBillT3())
                                                    .billStatus(BillStatus.NOTSERVED)
                                                    .build();
    public BillRequest orderRequestTableNo7 = BillRequest.builder()
                                                    .tableNo("7")
                                                    .orders(orderFixture.ordersForBillT7())
                                                    .billStatus(BillStatus.NOTSERVED)
                                                    .build();
    public BillRequest orderRequestWithDiffTableNo = BillRequest.builder()
                                                    .tableNo("1")
                                                    .orders(orderFixture.ordersForBillT7andT1())
                                                    .billStatus(BillStatus.NOTSERVED)
                                                    .build();
    
}
