package com.dish.perfect.global.log.logTrace;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.global.log.domain.TraceStatus;


public class LogManagerImplTest {

    private LogManager logManager = new LogManagerImpl();
    private TraceStatus status = logManager.begin("Test message");
    
    @Test
    @DisplayName("begin - traceStatus 생성")
    void begin(){
        assertNotNull(status);
        assertNotNull(status.getTrace());
        assertNotNull(status.getStartTime());
        assertNotNull(status.getMessage());

    }

    @Test
    @DisplayName("exception 생성")
    void exception(){
        TraceStatus statusA = logManager.begin("exTestA message");
        TraceStatus statusB = logManager.begin("exTestB message");

        logManager.exception(statusA, new IllegalArgumentException());
        logManager.exception(statusB, new RuntimeException());

    }
}
