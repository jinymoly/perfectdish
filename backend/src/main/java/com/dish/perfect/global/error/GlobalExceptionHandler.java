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
    protected ResponseEntity<ErrorResponse> handleException(final GlobalException exception,
            HttpServletRequest request) {
        log.warn("{} \n request:{}/{} \n exception:{}/{}",
                extractExceptionPoints(exception),
                request.getMethod(), request.getRequestURI(),
                exception.getErrorCode(), exception.getMessage());

        return ErrorResponse.from(exception.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleGeneralException(final Exception exception,
            HttpServletRequest request) {
        log.error("Uncaught exception: {} \n request:{}/{} \n message:{}",
                extractExceptionPoints(exception),
                request.getMethod(), request.getRequestURI(),
                exception.getMessage(), exception);

        return ResponseEntity.internalServerError().build();
    }

    private String extractExceptionPoints(Exception exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        if (stackTrace.length > 0) {
            return stackTrace[0].toString();
        }
        return "Unknown Source";
    }
}
