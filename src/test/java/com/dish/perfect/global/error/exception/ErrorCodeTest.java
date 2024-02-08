package com.dish.perfect.global.error.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorCodeTest {

    @Test
    @DisplayName("member error 발생")
    void duplicatedMemberErrorCode(){
        ErrorCode errorCode = ErrorCode.DUPLICATED_MEMBER;
        log.warn("{}, {}, {}", errorCode, errorCode.getStatus(), errorCode.getEmessage());
        assertEquals("U409", errorCode.getEcode());
    }

    @Test
    @DisplayName("menu error 발생")
    void notFoundMenuErrorCode(){
        ErrorCode errorCode = ErrorCode.NOT_FOUND_MENU;
        log.error("{}, {}, {}", errorCode, errorCode.getStatus(), errorCode.getEmessage());
        assertEquals(HttpStatus.NOT_FOUND, errorCode.getStatus());
    }
}
