package com.dish.perfect.global.auth;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dish.perfect.global.error.exception.ErrorCode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();

        if (requestURI.equals("/user/join") || requestURI.equals("/user/login") || requestURI.equals("/auth/refresh")) {
            log.info("JwtAuthFilter bypassing for: {}", requestURI);
            chain.doFilter(request, response);
            return;
        }

        String token = resolveToken(request);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                String phoneNumber = jwtTokenProvider.getPhoneNumberFromToken(token);
                String role = jwtTokenProvider.getRoleFromToken(token);
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                UserDetails userDetails = new User(phoneNumber, "", authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException(request, response, chain, ErrorCode.INVALID_TOKEN);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            ErrorCode errorCode) throws IOException, ServletException {
        request.setAttribute("exception", errorCode);
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
