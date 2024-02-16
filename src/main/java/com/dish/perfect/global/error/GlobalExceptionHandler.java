package com.dish.perfect.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ErrorResponse> handleMemberException(final GlobalException memberException){
        log.warn(memberException.getMessage(), memberException);
        
        return ErrorResponse.toResponseEntity(memberException.getErrorCode());
    }
}
