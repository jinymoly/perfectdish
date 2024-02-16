package com.dish.perfect.global.log.logTrace;

import com.dish.perfect.global.log.domain.Trace;
import com.dish.perfect.global.log.domain.TraceStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogManagerImpl implements LogManager{

    private static final String START_PREFIX = "--->";
    private static final String END_PREFIX = "<---";
    private static final String EX_PREFIX = "X---";

    private ThreadLocal<Trace> traceHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        initTraceId();
        Trace trace = traceHolder.get();
        Long startTime = System.currentTimeMillis();
        log.info("[{}] {}{}", trace.getId(), addSpace(START_PREFIX, trace.getDepth()), message);

        return new TraceStatus(trace, startTime, message);

    }
    
    @Override
    public void end(TraceStatus status) {
        completeThread(status, null);
    }
    
    @Override
    public void exception(TraceStatus status, Exception exception) {
        completeThread(status, exception);
    }
    
    private static String addSpace(String startPrefix, int depth) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < depth; i++){
            sb.append((i == depth - 1) ? "|" + startPrefix : "|   ");
        } 
        return sb.toString();
    }

    private void initTraceId() {
        Trace trace = traceHolder.get();
        if(trace == null){
            traceHolder.set(new Trace());
        } else {
            traceHolder.set(trace.createNextId());
        }
    }

    private void returnTraceId(){
        Trace trace = traceHolder.get();
        if(trace.isInitialCall()){
            traceHolder.remove();
        } else {
            traceHolder.set(trace.createPreId());
        }
    }

    private void completeThread(TraceStatus status, Exception exception){
        Long startTime = status.getStartTime();
        Long stopTime = System.currentTimeMillis();
        long duration = stopTime - startTime;
        Trace trace = status.getTrace();
        if(exception == null){
            log.info("[{}] {}{} duration={}ms", 
                            trace.getId(), 
                            addSpace(END_PREFIX, trace.getDepth()), 
                            status.getMessage(), 
                            duration);
            
        } else {
            log.info("[{}] {}{} duration={}ms exception={}", 
                            trace.getId(), 
                            addSpace(EX_PREFIX, trace.getDepth()), 
                            status.getMessage(), 
                            duration, 
                            exception.toString());
        }
        returnTraceId();
    }
    
}
