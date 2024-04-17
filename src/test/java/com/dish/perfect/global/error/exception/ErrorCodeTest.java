package com.dish.perfect.global.error.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorCodeTest {

    @Test
    @DisplayName("member error 발생")
    void duplicatedMemberErrorCode(){
        ErrorCode errorCode = ErrorCode.DUPLICATED_MEMBER;
        log.warn("{}, {}, {}", errorCode, errorCode.getEcode(), errorCode.getEmessage());
        assertEquals("10000", errorCode.getEcode());
    }

    @Test
    @DisplayName("menu error 발생")
    void notFoundMenuErrorCode(){
        ErrorCode errorCode = ErrorCode.NOT_FOUND_MENU;
        log.error("{}, {}, {}", errorCode, errorCode.getEcode(), errorCode.getEmessage());
        assertEquals("20001", errorCode.getEcode());
    }
}
