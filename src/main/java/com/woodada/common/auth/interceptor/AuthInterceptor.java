package com.woodada.common.auth.interceptor;

import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.auth.exception.AuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String REISSUE_TOKEN_END_POINT = "/api/v1/token";
    private static final String REFRESH_TOKEN_KEY_FORMAT = "member::%d::string::refresh_token";

    private final JwtHandler jwtHandler;
    private final StringRedisTemplate redisTemplate;

    public AuthInterceptor(final JwtHandler jwtHandler, final StringRedisTemplate redisTemplate) {
        this.jwtHandler = jwtHandler;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (REISSUE_TOKEN_END_POINT.equals(request.getRequestURI())) {
            final Cookie refreshTokenCookie = extractRefreshTokenCookie(request);
            final String refreshToken = refreshTokenCookie.getValue();
            final Long memberId = decodeRefreshTokne(refreshToken);
            final String refreshTokenKey = String.format(REFRESH_TOKEN_KEY_FORMAT, memberId);
            final String redisRefreshToken = redisTemplate.opsForValue().get(refreshTokenKey);

            if (!refreshToken.equals(redisRefreshToken)) {
                throw new AuthenticationException("INVALID_REFRESH_TOKEN");
            }

            request.setAttribute("memberId", memberId);
            return true;
        }

        final String authHeader = getAuthHeader(request);
        final Long memberId = decodeAccessToken(authHeader);
        request.setAttribute("memberId", memberId);
        return true;
    }

    private Long decodeAccessToken(String authHeader) {
        try {
            return jwtHandler.decodeTokenWithHeader(authHeader);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException(e);
        }
    }

    private Long decodeRefreshTokne(String refreshToken) {
        try {
            return jwtHandler.decodeToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException(e);
        }
    }

    private Cookie extractRefreshTokenCookie(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
            .filter(cookie -> Token.REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
            .findFirst()
            .orElseThrow(() -> new AuthenticationException("NOT_FOUND_REFRESH_TOKEN"));
    }

    private String getAuthHeader(final HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return Objects.requireNonNullElse(authHeader, "");
    }

}
