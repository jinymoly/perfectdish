package com.dish.perfect.global.error;

import org.springframework.http.ResponseEntity;

import com.dish.perfect.global.error.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;

    public static ResponseEntity<ErrorResponse> from(ErrorCode errorCode) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .errorCode(errorCode.getEcode())
                        .errorMessage(errorCode.getEmessage())
                        .build());

    }
}
