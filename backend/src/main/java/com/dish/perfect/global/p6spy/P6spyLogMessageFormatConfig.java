package com.dish.perfect.global.p6spy;

import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.spy.P6SpyOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class P6spyLogMessageFormatConfig {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spyQueryFormat.class.getName());
    }
}
