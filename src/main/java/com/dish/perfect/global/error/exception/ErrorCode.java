package com.dish.perfect.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Global [00000]
    // 입력값 오류
    INVALID_INPUT_ERROR("00000", "잘못된 입력값 입니다."),

    // Member [10000]
    // 이미 존재하는 사용자
    DUPLICATED_MEMBER("10000", "이미 존재하는 회원입니다."),
    DUPLICATED_MEMBER_INFO("10001", "이미 존재하는 회원입니다."),

    // 존재하지 않는 사용자
    NOT_FOUND_MEMBER("10002", "회원 목록이 존재하지 않습니다."),
    // TODO : 삭제된 사용자 - soft delete 처리, isActive
    ALREADY_DELETED_MEMBER("10003", "이미 삭제된 회원입니다."),
    FAIL_CREATE_MEMBER("10004", "회원 가입 실패하였습니다."),

    // Menu [20000]
    // 같은 메뉴가 있다.
    DUPLICATED_MENU("20000", "이미 존재하는 메뉴입니다."),
    // 존재하지 않는 메뉴
    NOT_FOUND_MENU("20001", "해당 메뉴가 존재하지 않습니다."),
    // 금액 오류
    INVALID_MINIMUM_PRICE("20002", "최소 금액은 5000원입니다."),
    // TODO : 올바르지 않은 이미지 파일
    INVALID_IMAGE("20003", "올바르지 않은 이미지 파일입니다."),
    // 메뉴 저장 실패
    FAIL_CREATE_MENU("20004", "메뉴 등록 실패하였습니다."),
    // 할인 메뉴 없음
    NOT_FOUND_D_MENU("20005", "할인 메뉴가 존재하지 않습니다."),
    ALREADY_UNAVAILABLE_MENU("20006", "이미 UNAVAILABLE 메뉴입니다."),

    // MenuBoard [30000]
    // 메뉴보드 등록 실패
    FAIL_CREATE_MENU_BOARD("30000", "메뉴 보드 등록 실패하였습니다."),
    // 메뉴보드 조회 실패
    NOT_FOUND_MENU_BOARD("30001", "메뉴 보드가 존재하지 않습니다."),
    NOT_FOUND_MENU_BOARD_COMMONS("30002", "common메뉴가 존재하지 않습니다."),
    NOT_FOUND_MENU_BOARD_DISCOUNTS("30003", "discount메뉴가 존재하지 않습니다."),

    // Order [40000]
    // 주문이 저장 실패
    FAIL_CREATE_ORDER("40001", "주문 실패하였습니다."),
    // 존재하지 않는 주문
    NOT_FOUND_ORDER("40002", "해당 주문이 존재하지 않습니다."), 

    ;

    private final String Ecode;
    private final String Emessage;

}
