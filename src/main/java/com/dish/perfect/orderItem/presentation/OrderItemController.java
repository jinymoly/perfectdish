package com.dish.perfect.orderItem.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dish.perfect.orderItem.domain.OrderItem;
import com.dish.perfect.orderItem.domain.OrderItemStatus;
import com.dish.perfect.orderItem.dto.request.OrderItemRequest;
import com.dish.perfect.orderItem.dto.response.OrderItemResponse;
import com.dish.perfect.orderItem.service.OrderItemCoreService;
import com.dish.perfect.orderItem.service.OrderItemPresentationService;

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
@RequestMapping("/orderItem")
public class OrderItemController {
    
    private final OrderItemCoreService orderItemCoreService;
    private final OrderItemPresentationService orderItemPresentationService;

    @GetMapping("/create")
    public String addOrderItemRequest() {
        return "OrderItemRequest 페이지로 이동";
    }

    @PostMapping("/create")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody @Valid OrderItemRequest orderItemRequest){
        OrderItem newItem = orderItemCoreService.createOrderItem(orderItemRequest);
        return ResponseEntity.ok(newItem);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderItemResponse>> allItems(){
        List<OrderItemResponse> all = orderItemPresentationService.findAllOrderItem();
        return ResponseEntity.ok(all);
    }

    /**
     * status가 CREATED 목록
     * @param status
     * @return
     */
    @GetMapping("/all/created")
    public ResponseEntity<List<OrderItemResponse>> itemsByStatus (OrderItemStatus status) {
        List<OrderItemResponse> itemByStatus = orderItemPresentationService.findbyOrderItemStatus(OrderItemStatus.CREATED);
        return ResponseEntity.ok(itemByStatus);
    }

    /**
     * status CREATED -> COMPLETED
     * @param id
     * @return
     */
    @PatchMapping("/{id}/editstatus")
    public ResponseEntity<Void> switchOrderItemStatus(@PathVariable("id") Long id){
        OrderItem orderItem = orderItemPresentationService.findOrderItemById(id);
        OrderItem updated = orderItemCoreService.updateOrderItemStatus(orderItem.getId());
        log.info("item:{} / {}", updated.getMenu().getMenuName(), updated.getOrderItemStatus());
        return ResponseEntity.ok().build();
    }
}
