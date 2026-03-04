package com.dish.perfect.global.log;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.global.log.domain.TraceStatus;
import com.dish.perfect.global.log.logTrace.LogManager;

public class LoggingAspectTest {

    private LoggingAspect loggingAspect;
    private LogManager logManager;

    @BeforeEach
    void setUp() {
        logManager = mock(LogManager.class);
        loggingAspect = new LoggingAspect(logManager);
    }

    @Test
    @DisplayName("Aspect calls begin and end on LogManager")
    void logCall_CallsBeginAndEnd() throws Throwable {
        // Arrange
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("TestService.testMethod()");
        when(joinPoint.getArgs()).thenReturn(new Object[] { "arg1" });
        when(logManager.begin(anyString())).thenReturn(new TraceStatus(null, 0L, "message"));

        // Act
        loggingAspect.logCall(joinPoint);

        // Assert
        verify(logManager).begin(anyString());
        verify(joinPoint).proceed();
        verify(logManager).end(any(TraceStatus.class), any());
    }

    @Test
    @DisplayName("Aspect calls exception on LogManager when exception occurs")
    void logCall_CallsException() throws Throwable {
        // Arrange
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("TestService.testMethod()");
        when(joinPoint.getArgs()).thenReturn(new Object[] {});
        when(logManager.begin(anyString())).thenReturn(new TraceStatus(null, 0L, "message"));
        when(joinPoint.proceed()).thenThrow(new RuntimeException("Test Exception"));

        // Act & Assert
        try {
            loggingAspect.logCall(joinPoint);
        } catch (RuntimeException e) {
            // expected
        }

        verify(logManager).begin(anyString());
        verify(logManager).exception(any(TraceStatus.class), any(Exception.class));
    }
}
