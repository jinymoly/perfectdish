package com.dish.perfect.global.error.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{
    
    private ErrorCode errorCode;

    public MemberException(ErrorCode errorCode) {
        super(errorCode.getEmessage());
        this.errorCode = errorCode;
    }

    public MemberException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    
}
