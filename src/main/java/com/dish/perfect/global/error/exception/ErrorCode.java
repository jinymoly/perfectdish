package com.dish.perfect.global.error.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 입력값 오류
    INVALID_INPUT_ERROR(HttpStatus.BAD_REQUEST, "400", "잘못된 입력값 입니다."),

    // member
    // 이미 존재하는 사용자
    DUPLICATED_MEMBER(HttpStatus.CONFLICT, "U409", "이미 존재하는 회원입니다."),
    // 존재하지 않는 사용자
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "U404", "존재하지 않는 회원입니다."),
    // TODO : 삭제된 사용자 - soft delete 처리, isActive
    ALREADY_DELETED_MEMBER(HttpStatus.GONE, "U410", "이미 삭제된 회원입니다."),
    
    // menu
    // 같은 메뉴가 있다.
    DUPLICATED_MENU(HttpStatus.CONFLICT, "M409", "이미 존재하는 메뉴입니다."),
    // 존재하지 않는 메뉴
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "M404", "해당 메뉴가 존재하지 않습니다."),
    // 금액 오류
    INVALID_MINIMUM_PRICE(HttpStatus.BAD_REQUEST, "M4001", "최소 금액은 5000원입니다."),
    // TODO : 올바르지 않은 이미지 파일
    INVALID_IMAGE(HttpStatus.BAD_REQUEST, "M4002", "올바르지 않은 이미지 파일입니다."),
    // 메뉴 저장 실패
    FAIL_CREATE_MENU(HttpStatus.BAD_REQUEST,"M403", "메뉴 등록 실패하였습니다."),
    // 할인 메뉴 없음
    NOT_FOUND_D_MENU(HttpStatus.NOT_FOUND, "M405", "할인 메뉴가 존재하지 않습니다."),

    // menuBoard
    // 메뉴보드 등록 실패
    FAIL_CREATE_MENU_BOARD(HttpStatus.BAD_REQUEST,"B400", "메뉴보드 등록 실패하였습니다."),
    // 메뉴보드 조회 실패
    NOT_FOUND_MENU_BOARD(HttpStatus.NOT_FOUND, "M403", "메뉴 보드가 존재하지 않습니다."),
    NOT_FOUND_MENU_BOARD_COMMONS(HttpStatus.NOT_FOUND, "M401", "common메뉴가 존재하지 않습니다."),
    NOT_FOUND_MENU_BOARD_DISCOUNTS(HttpStatus.NOT_FOUND, "B402", "discount메뉴가 존재하지 않습니다."),
    
    // order
    // 주문이 저장 실패
    FAIL_CREATE_ORDER(HttpStatus.BAD_REQUEST,"O400", "주문 실패하였습니다."),
    // 존재하지 않는 주문
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "O401", "해당 주문이 존재하지 않습니다."),
    
    
    ;

    private final HttpStatus status;
    private final String Ecode;
    private final String Emessage;

}
