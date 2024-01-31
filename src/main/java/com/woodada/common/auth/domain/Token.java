package com.woodada.common.auth.domain;

import java.time.Duration;
import org.springframework.util.ObjectUtils;

public record Token(
    String accessToken,
    String refreshToken
) {

    public Token {
        if (ObjectUtils.isEmpty(accessToken)) {
            throw new IllegalArgumentException("access token 이 입력되지 않았습니다.");
        }
        if (ObjectUtils.isEmpty(refreshToken)) {
            throw new IllegalArgumentException("refresh token 이 입력되지 않았습니다.");
        }
    }

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final long ACCESS_TOKEN_EXPIRATION_PERIOD = Duration.ofHours(2).toSeconds();
    public static final long REFRESH_TOKEN_EXPIRATION_PERIOD = Duration.ofMinutes(24 * 60 * 7).toSeconds();
}
