package com.dish.perfect.order.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.order.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderPresentationService {

    private final OrderRepository orderRepository;

    // 테이블 별 주문을 반환
    //new GlobalException(ErrorCode.NOT_FOUND_ORDER, order.getTableNo()+"번 테이블 주문이 존재하지 않습니다.");

    // tableNo completed 되지 않은 목록 반환 
    //log.info("모든 음식이 나왔습니다.");

    
}
