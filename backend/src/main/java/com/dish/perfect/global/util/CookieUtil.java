package com.dish.perfect.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {

    @Value("${cookie.secure:false}")
    private boolean cookieSecure;

    /**
     * Set secure HttpOnly cookie with SameSite attribute
     * 
     * @param response HTTP response
     * @param name     Cookie name
     * @param value    Cookie value
     * @param maxAge   Max age in seconds
     */
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure); // Environment-based: false for local, true for prod
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        // SameSite=Lax for better UX while maintaining security
        // Note: SameSite attribute is not directly supported by Cookie class,
        // so we use Set-Cookie header
        response.addHeader("Set-Cookie", String.format(
                "%s=%s; Path=/; Max-Age=%d; HttpOnly%s; SameSite=Lax",
                name, value, maxAge, cookieSecure ? "; Secure" : ""));
    }

    /**
     * Clear cookie by setting max age to 0
     * 
     * @param response HTTP response
     * @param name     Cookie name
     */
    public void clearCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
