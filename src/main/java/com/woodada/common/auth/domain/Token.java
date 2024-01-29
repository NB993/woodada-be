package com.woodada.common.auth.domain;

import java.time.Duration;
import java.util.Objects;

public record Token(
    String accessToken,
    String refreshToken
) {

    public Token {
        Objects.requireNonNull(accessToken);
        Objects.requireNonNull(refreshToken);
    }

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final long ACCESS_TOKEN_EXPIRATION_PERIOD = Duration.ofHours(2).toSeconds();
    public static final long REFRESH_TOKEN_EXPIRATION_PERIOD = Duration.ofMinutes(24 * 60 * 7).toSeconds();
}
