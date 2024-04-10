package com.woodada.common.auth.application.port.in;

import com.woodada.common.auth.application.port.out.RefreshTokenSavePort;
import com.woodada.common.auth.application.service.ReIssueTokenService;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.Token;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReIssueTokenUseCase 테스트")
class ReIssueTokenUseCaseTest {

    private ReIssueTokenService reIssueService;

    @Mock private JwtHandler jwtHandler;
    @Mock private RefreshTokenSavePort refreshTokenSavePort;

    @BeforeEach
    void setUp() {
        reIssueService = new ReIssueTokenService(jwtHandler, refreshTokenSavePort);
    }

    @DisplayName("멤버 ID를 전달받으면 엑세스토큰과 리프레시 토큰을 재발급하고, 레디스에 재발급된 리프레시 토큰을 저장한다.")
    @Test
    void testReIssue() {
        //given
        final Instant issuedAt = Instant.now();
        Mockito.when(jwtHandler.createToken(1L, Token.ACCESS_TOKEN_EXPIRATION_PERIOD, issuedAt))
            .thenReturn("access_token");
        Mockito.when(jwtHandler.createToken(1L, Token.REFRESH_TOKEN_EXPIRATION_PERIOD, issuedAt))
            .thenReturn("refresh_token");

        //when
        final Token reissuedToken = reIssueService.reIssue(1L, issuedAt);

        //then
        Mockito.verify(refreshTokenSavePort).save(1L, "refresh_token");
    }
}
