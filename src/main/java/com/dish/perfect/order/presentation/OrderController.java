package com.dish.perfect.order.presentation;

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

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest orderRequest){
        Order newOrder = orderCoreService.createOrder(orderRequest);
        return ResponseEntity.ok(newOrder);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> alls(){
        List<OrderResponse> all = orderPresentationService.findAllOrder();
        return ResponseEntity.ok(all);
    }

    /**
     * status가 CREATED 목록
     * @param status
     * @return
     */
    @GetMapping("/all/created")
    public ResponseEntity<List<OrderResponse>> sByStatus (OrderStatus status) {
        List<OrderResponse> ByStatus = orderPresentationService.findbyOrderStatus(OrderStatus.CREATED);
        return ResponseEntity.ok(ByStatus);
    }

    /**
     * status CREATED -> COMPLETED
     * @param id
     * @return
     */
    @PatchMapping("/{id}/editstatus")
    public ResponseEntity<Void> switchOrderStatus(@PathVariable("id") Long id){
        Order order = orderPresentationService.findOrderById(id);
        Order updated = orderCoreService.updateOrderStatus(order.getId());
        log.info(":{} / {}", updated.getOrderInfo().getMenu().getMenuName(), updated.getOrderStatus());
        return ResponseEntity.ok().build();
    }
}
