package com.dish.perfect.global.log;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.dish.perfect.global.log.domain.TraceStatus;
import com.dish.perfect.global.log.logTrace.LogManager;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final LogManager logManager;

    @Around("execution(* com.dish.perfect..repository.*.*(..)) ||" +
            "execution(* com.dish.perfect..service.*.*(..)) ||" +
            "execution(* com.dish.perfect..controller.*.*(..))")
    public Object logCall(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                message += " args=" + Arrays.toString(args);
            }

            status = logManager.begin(message);

            Object result = joinPoint.proceed();

            logManager.end(status, result);
            return result;
        } catch (Exception e) {
            logManager.exception(status, e);
            throw e;
        }
    }
}
