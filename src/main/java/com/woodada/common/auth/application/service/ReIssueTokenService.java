package com.woodada.common.auth.application.service;

import com.woodada.common.auth.application.port.in.ReIssueTokenUseCase;
import com.woodada.common.auth.application.port.out.RefreshTokenSavePort;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.Token;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class ReIssueTokenService implements ReIssueTokenUseCase {

    private final JwtHandler jwtHandler;
    private final RefreshTokenSavePort refreshTokenSavePort;

    public ReIssueTokenService(final JwtHandler jwtHandler, final RefreshTokenSavePort refreshTokenSavePort) {
        this.jwtHandler = jwtHandler;
        this.refreshTokenSavePort = refreshTokenSavePort;
    }

    @Override
    public Token reIssue(final Long memberId, final Instant issuedAt) {
        final String accessToken = jwtHandler.createToken(memberId, Token.ACCESS_TOKEN_EXPIRATION_PERIOD, issuedAt);
        final String refreshToken = jwtHandler.createToken(memberId, Token.REFRESH_TOKEN_EXPIRATION_PERIOD, issuedAt);

        refreshTokenSavePort.save(memberId, refreshToken);
        return new Token(accessToken, refreshToken);
    }
}
