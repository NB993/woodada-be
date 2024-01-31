package com.woodada.common.auth.application.service;

import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class OAuth2LoginService implements OAuth2LoginUseCase {

    private final JwtHandler jwtHandler;

    public OAuth2LoginService(final JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    public Token login(final ProviderType providerType, final String code) {

        return new Token(
            jwtHandler.createToken("email", Token.ACCESS_TOKEN_EXPIRATION_PERIOD, Instant.now()),
            jwtHandler.createToken("email", Token.REFRESH_TOKEN_EXPIRATION_PERIOD, Instant.now())
        );
    }
}
