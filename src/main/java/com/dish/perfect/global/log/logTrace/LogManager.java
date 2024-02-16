package com.dish.perfect.global.log.logTrace;

import com.dish.perfect.global.log.domain.TraceStatus;

public interface LogManager {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception exception);
    
}
