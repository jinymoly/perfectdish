package com.dish.perfect.global.log.logTrace;

import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.dish.perfect.global.log.domain.Trace;
import com.dish.perfect.global.log.domain.TraceStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogManagerImpl implements LogManager {

    private static final String START_PREFIX = "--->";
    private static final String END_PREFIX = "<---";
    private static final String EX_PREFIX = "<X--";

    @Override
    public TraceStatus begin(String message) {
        String traceId = MDC.get("traceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put("traceId", traceId);
        }

        Long startTime = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId, START_PREFIX, message);

        // Store traceId in TraceStatus to keep context if needed, though MDC handles it
        // per thread
        return new TraceStatus(new Trace(traceId, 0), startTime, message);
    }

    @Override
    public void end(TraceStatus status) {
        completeThread(status, null, null);
    }

    @Override
    public void end(TraceStatus status, Object result) {
        completeThread(status, null, result);
    }

    @Override
    public void exception(TraceStatus status, Exception exception) {
        completeThread(status, exception, null);
    }

    private static String addSpace(String startPrefix, int depth) {
        // Simplified for MDC version, depth handling would require more complex MDC
        // management or ThreadLocal
        return startPrefix;
    }

    // Removed initTraceId and returnTraceId as we rely on MDC/Filter for ID
    // generation
    // Depth handling is simplified for this iteration to focus on TraceID
    // propagation

    private void completeThread(TraceStatus status, Exception exception, Object result) {
        if (status == null) {
            return;
        }
        Long startTime = status.getStartTime();
        Long stopTime = System.currentTimeMillis();
        long duration = stopTime - startTime;
        Trace trace = status.getTrace();
        if (exception == null) {
            String resultStr = (result != null) ? " result=" + result.toString() : "";
            log.info("[{}] {}{}{} duration={}ms",
                    trace.getId(),
                    addSpace(END_PREFIX, trace.getDepth()),
                    status.getMessage(),
                    resultStr,
                    duration);

        } else {
            log.info("[{}] {}{} duration={}ms exception={}",
                    trace.getId(),
                    addSpace(EX_PREFIX, trace.getDepth()),
                    status.getMessage(),
                    duration,
                    exception.toString());
        }
    }

}
