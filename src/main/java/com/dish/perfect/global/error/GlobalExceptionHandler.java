package com.dish.perfect.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ErrorResponse> handleException(final GlobalException exception, HttpServletRequest request){
        log.warn("{} \n request:{}/{} \n exception:{}/{}",
                    extractExceptionPoints(exception),
                    request.getMethod(), request.getRequestURI(),
                    exception.getErrorCode(), exception.getMessage());
        
        return ErrorResponse.from(exception.getErrorCode());
    }

    private String extractExceptionPoints(Exception exception){
        StackTraceElement[] stackTrace = exception.getStackTrace();
        return stackTrace[0].toString();
    }
}
