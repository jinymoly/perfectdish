package com.dish.perfect.global.log.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TraceTest {

    @Test
    @DisplayName("NextLogTrace 생성")
    void createNextTraceA() {
        Trace trace = new Trace("test_trace", 0);
        Trace nextTrace = trace.createNextId();

        assertEquals(trace.getDepth(), nextTrace.getDepth() - 1);
    }

    @Test
    @DisplayName("PreLogTrace 생성")
    void createNextTraceB() {
        Trace trace = new Trace("test_trace", 3);
        Trace preTrace = trace.createPreId();

        assertEquals(trace.getDepth() - 1, preTrace.getDepth());
    }

    @Test
    @DisplayName("초기 호출인가")
    void isInitialCallA() {
        Trace trace = new Trace("test_trace", 0);
        assertTrue(trace.isInitialCall());
    }

    @Test
    @DisplayName("초기 호출 아닌가")
    void isInitialCallB() {
        Trace trace = new Trace("test_trace", 3);
        assertFalse(trace.isInitialCall());
    }

}
