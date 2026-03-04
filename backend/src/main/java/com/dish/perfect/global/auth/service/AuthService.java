package com.dish.perfect.global.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dish.perfect.global.auth.JwtTokenProvider;
import com.dish.perfect.global.auth.domain.RefreshToken;
import com.dish.perfect.global.auth.domain.RefreshTokenRepository;
import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public void saveRefreshToken(String phoneNumber, String refreshToken) {
        RefreshToken token = new RefreshToken(phoneNumber, refreshToken);
        refreshTokenRepository.save(token);
        log.info("Refresh token saved for user: {}", phoneNumber);
    }

    // RTR
    public TokenPair refreshAccessToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new GlobalException(ErrorCode.INVALID_TOKEN);
        }
        String phoneNumber = jwtTokenProvider.getPhoneNumberFromToken(refreshToken);

        Optional<RefreshToken> storedTokenOpt = refreshTokenRepository.findById(phoneNumber);

        if (storedTokenOpt.isEmpty()) {
            log.warn("Refresh token not found in Redis for user: {} - possible reuse attempt", phoneNumber);
            throw new GlobalException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        RefreshToken storedToken = storedTokenOpt.get();

        if (!storedToken.getRefreshToken().equals(refreshToken)) {
            log.error("SECURITY ALERT: Refresh token reuse detected for user: {}", phoneNumber);
            revokeRefreshToken(phoneNumber);
            throw new GlobalException(ErrorCode.REFRESH_TOKEN_REUSE_DETECTED);
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(phoneNumber, getRoleForUser(phoneNumber));
        String newRefreshToken = jwtTokenProvider.createRefreshToken(phoneNumber);

        refreshTokenRepository.delete(storedToken);
        saveRefreshToken(phoneNumber, newRefreshToken);

        log.info("Access token refreshed for user: {}", phoneNumber);

        return new TokenPair(newAccessToken, newRefreshToken);
    }

    public void revokeRefreshToken(String phoneNumber) {
        refreshTokenRepository.deleteById(phoneNumber);
        log.info("Refresh token revoked for user: {}", phoneNumber);
    }

    public boolean validateRefreshToken(String phoneNumber, String refreshToken) {
        Optional<RefreshToken> storedToken = refreshTokenRepository.findById(phoneNumber);
        return storedToken.isPresent() && storedToken.get().getRefreshToken().equals(refreshToken);
    }

    private String getRoleForUser(String phoneNumber) {
        Member member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new GlobalException(ErrorCode.UNAUTHORIZED));
        return member.getRole().toString();
    }

    public static class TokenPair {
        private final String accessToken;
        private final String refreshToken;

        public TokenPair(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
