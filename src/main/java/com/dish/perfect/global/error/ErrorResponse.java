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
    
    private  int status;
    private  String errorCode;
    private  String errorMessage;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode){
        return ResponseEntity.status(errorCode.getStatus())
                                .body(ErrorResponse.builder()
                                                    .status(errorCode.getStatus().value())
                                                    .errorCode(errorCode.getEcode())
                                                    .errorMessage(errorCode.getEmessage())
                                                    .build());
                                                    
    }
}
