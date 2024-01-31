package com.woodada.common.auth.domain;

import org.springframework.stereotype.Component;

@Component
public class JwtHandler {

    public String createToken(final String email, long tokenExpirationPeriod) {
        return "token value";
    }
}
