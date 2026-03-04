package com.dish.perfect.global.log.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraceStatus {
    
    private Trace trace;
    private Long startTime;
    private String message;
}
