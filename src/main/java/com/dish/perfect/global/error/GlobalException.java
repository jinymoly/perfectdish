package com.dish.perfect.global.error;

import com.dish.perfect.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    
    private ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getEmessage());
        this.errorCode = errorCode;
    }

    public GlobalException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    
}
