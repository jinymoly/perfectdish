package com.dish.perfect.order.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dish.perfect.order.domain.Order;
import com.dish.perfect.order.domain.OrderStatus;
import com.dish.perfect.order.dto.request.OrderRequest;
import com.dish.perfect.order.dto.response.OrderResponse;
import com.dish.perfect.order.service.OrderCoreService;
import com.dish.perfect.order.service.OrderPresentationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderController {

    private final OrderCoreService orderCoreService;
    private final OrderPresentationService orderPresentationService;

    @GetMapping("/create")
    public String addOrderRequest() {
        return "OrderRequest 페이지로 이동";
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        log.info("Order creation request: menu={}, qty={}, table={}, phone={}", 
            orderRequest.getMenuName(), orderRequest.getQuantity(), orderRequest.getTableNo(), orderRequest.getPhoneNumber());
        Order newOrder = orderCoreService.createOrder(orderRequest);
        return ResponseEntity.ok(OrderResponse.fromOrderResponse(newOrder));
    }

    @PostMapping("/batch")
    public ResponseEntity<java.util.List<OrderResponse>> createBatchOrder(@RequestBody @Valid com.dish.perfect.order.dto.request.BatchOrderRequest batchOrderRequest) {
        log.info("Batch order request: table={}, phone={}, items={}", 
            batchOrderRequest.getTableNo(), batchOrderRequest.getPhoneNumber(), batchOrderRequest.getItems().size());
        java.util.List<Order> newOrders = orderCoreService.createBatchOrder(batchOrderRequest);
        java.util.List<OrderResponse> responses = newOrders.stream()
            .map(OrderResponse::fromOrderResponse)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> alls() {
        List<OrderResponse> all = orderPresentationService.findAllOrder();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/my/{phoneNumber}")
    public ResponseEntity<List<OrderResponse>> myOrders(@PathVariable("phoneNumber") String phoneNumber) {
        List<OrderResponse> my = orderPresentationService.getOrdersByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(my);
    }

    /**
     * status가 CREATED 목록
     * 
     * @param status
     * @return
     */
    @GetMapping("/all/created")
    public ResponseEntity<List<OrderResponse>> sByStatus(OrderStatus status) {
        List<OrderResponse> ByStatus = orderPresentationService.findbyOrderStatus(OrderStatus.CREATED);
        return ResponseEntity.ok(ByStatus);
    }

    /**
     * status CREATED -> COMPLETED
     * 
     * @param id
     * @return
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable("id") Long id,
            @RequestBody java.util.Map<String, String> request) {
        OrderStatus status = OrderStatus.valueOf(request.get("status"));
        orderCoreService.updateOrderStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
