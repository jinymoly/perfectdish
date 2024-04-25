package com.dish.perfect.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Global [00000]
    INVALID_INPUT_ERROR("00000", "잘못된 입력값입니다."),

    // Member [10000]
    DUPLICATED_MEMBER("10000", "이미 존재하는 회원입니다."),
    DUPLICATED_MEMBER_INFO("10001", "이미 존재하는 회원 정보입니다."),
    NOT_FOUND_MEMBER("10002", "회원 목록이 존재하지 않습니다."),
    ALREADY_DELETED_MEMBER("10003", "이미 삭제된 회원입니다."),
    FAIL_CREATE_MEMBER("10004", "회원 가입 실패하였습니다."),

    // Menu [20000]
    DUPLICATED_MENU("20000", "이미 존재하는 메뉴입니다."),
    NOT_FOUND_MENU("20001", "해당 메뉴가 존재하지 않습니다."),
    INVALID_MINIMUM_PRICE("20002", "최소 금액이 부족합니다."),
    INVALID_IMAGE("20003", "올바르지 않은 이미지 파일입니다."),
    FAIL_CREATE_MENU("20004", "메뉴 등록 실패하였습니다."),
    NOT_FOUND_D_MENU("20005", "할인 메뉴가 존재하지 않습니다."),
    ALREADY_UNAVAILABLE_MENU("20006", "이미 UNAVAILABLE 메뉴입니다."),

    // Order [30000]
    NOT_FOUND_ORDER("30001", "해당 주문이 존재하지 않습니다."),
    FAIL_CREATE_ORDER("30001", "주문 생성 실패하였습니다."),
    ALREADY_COMPLETED_ORDER("30003", "이미 모든 주문이 처리되었습니다."),
    
    // Bill [40000]
    NOT_FOUND_BILL("40001", "해당 청구서가 존재하지 않습니다."), 
    NOT_FOUND_BILL_BY_TABLE("40002", "해당 테이블의 청구서가 존재하지 않습니다."), 
    FAIL_CREATE_BILL("40003", "청구서 생성 실패하였습니다."),
    ALREADY_COMPLETED_BILL("40004", "이미 모든 음식이 제공되었습니다."),

    ;

    private final String Ecode;
    private final String Emessage;

}
