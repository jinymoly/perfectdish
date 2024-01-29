package com.dish.perfect.global.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dish.perfect.global.log.logTrace.LogManager;
import com.dish.perfect.global.log.logTrace.LogManagerImpl;

@Configuration
public class LogTraceConfig {
    
    @Bean
    public LogManager logProcessor(){
        return new LogManagerImpl();
    }
}
