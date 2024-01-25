package com.dish.perfect.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dish.perfect.global.error.exception.MemberException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MemberException.class)
    protected ResponseEntity<ErrorResponse> handleMemberException(final MemberException memberException){
        log.warn(memberException.getMessage(), memberException);
        
        return ErrorResponse.toResponseEntity(memberException.getErrorCode());
    }
}
