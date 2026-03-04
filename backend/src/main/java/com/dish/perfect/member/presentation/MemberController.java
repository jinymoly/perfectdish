package com.dish.perfect.member.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dish.perfect.global.auth.JwtTokenProvider;
import com.dish.perfect.global.auth.service.AuthService;
import com.dish.perfect.global.util.CookieUtil;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberLoginRequest;
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.service.MemberCoreService;

import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class MemberController {

    private final MemberCoreService memberCoreService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @GetMapping("/join")
    public String addMemberRequest() {
        return "memberRequest 페이지로 이동";
    }

    @PostMapping("/join")
    public ResponseEntity<Member> addMember(@RequestBody @Valid MemberRequest memberRequest) {
        log.info("Signup attempt for user: {}", memberRequest.getUserName());
        Member newMember = memberCoreService.join(memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
        log.info("Login attempt for user: {}", memberLoginRequest.getPhoneNumber());
        Member member = memberCoreService.login(memberLoginRequest);

        // Generate access token and refresh token
        String accessToken = jwtTokenProvider.createAccessToken(member.getPhoneNumber(), member.getRole().toString());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getPhoneNumber());

        // Save refresh token to Redis
        authService.saveRefreshToken(member.getPhoneNumber(), refreshToken);

        // Set tokens as HttpOnly cookies with SameSite=Lax
        cookieUtil.setCookie(response, "accessToken", accessToken, 30 * 60); // 30 minutes
        cookieUtil.setCookie(response, "refreshToken", refreshToken, 7 * 24 * 60 * 60); // 7 days

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("phoneNumber", member.getPhoneNumber());
        // loginInfo.put("name", member.getUserName()); // Omitted for Commit 7
        loginInfo.put("role", member.getRole().toString());
        loginInfo.put("accessToken", accessToken);
        loginInfo.put("refreshToken", refreshToken);

        log.info("User logged in: {}", member.getPhoneNumber());

        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }
}
}
