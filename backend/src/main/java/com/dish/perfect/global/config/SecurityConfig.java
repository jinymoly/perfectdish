package com.dish.perfect.global.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dish.perfect.global.auth.JwtAuthFilter;
import com.dish.perfect.global.auth.JwtAuthenticationEntryPoint;
import com.dish.perfect.global.auth.JwtTokenProvider;
import com.dish.perfect.global.log.logTrace.LogTraceFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        return new JwtAuthFilter(jwtTokenProvider);
    }

    @Bean
    public LogTraceFilter logTraceFilter() {
        return new LogTraceFilter();
    }

    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity, JwtAuthFilter jwtAuthFilter,
            LogTraceFilter logTraceFilter) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(
                        a -> a.requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/index.html"),
                                new AntPathRequestMatcher("/css/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/images/**"),
                                new AntPathRequestMatcher("/favicon.ico"),
                                new AntPathRequestMatcher("/user/join"),
                                new AntPathRequestMatcher("/user/login"),
                                new AntPathRequestMatcher("/menu/available"),
                                new AntPathRequestMatcher("/menu_images/**"),
                                new AntPathRequestMatcher("/user/allmember"),
                                new AntPathRequestMatcher("/order/all"),
                                new AntPathRequestMatcher("/order/create"),
                                new AntPathRequestMatcher("/order/batch"),
                                new AntPathRequestMatcher("/order/my/**"),
                                new AntPathRequestMatcher("/order/*/status"),
                                new AntPathRequestMatcher("/bill/all"),
                                new AntPathRequestMatcher("/bill/*/editStatus"),
                                new AntPathRequestMatcher("/menu/all"),
                                new AntPathRequestMatcher("/menu/*/stock"),
                                new AntPathRequestMatcher("/auth/refresh"))
                                .permitAll().anyRequest().authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 방식 사용 X
                .addFilterBefore(logTraceFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, LogTraceFilter.class)
                .build();
    }

    @Bean // TO-BE::: front end cors - 나중에 따로 ㅇ관리할 때
    CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3030"));
        configuration.setAllowedMethods(Arrays.asList("*")); // 모든 HTTP메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 값 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 url 패턴에 대해 cors 허용 설정
        return source;

    }

    @Bean
    public PasswordEncoder makePassword() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
