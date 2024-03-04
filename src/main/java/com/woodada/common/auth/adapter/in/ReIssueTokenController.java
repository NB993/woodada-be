package com.woodada.common.auth.adapter.in;


import com.woodada.common.auth.adapter.in.response.ReIssueTokenResponse;
import com.woodada.common.auth.application.port.in.ReIssueTokenUseCase;
import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.support.ApiResponse;
import java.time.Instant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class ReIssueTokenController {

    private final ReIssueTokenUseCase reIssueTokenUseCase;

    public ReIssueTokenController(final ReIssueTokenUseCase reIssueTokenUseCase) {
        this.reIssueTokenUseCase = reIssueTokenUseCase;
    }

    @PostMapping
    ResponseEntity<ApiResponse<ReIssueTokenResponse>> reIssueToken(WddMember wddMember) {
        final Token token = reIssueTokenUseCase.reIssue(wddMember.getId(), Instant.now());
        final ResponseCookie refreshTokenCookie = ResponseCookie.from(Token.REFRESH_TOKEN_COOKIE_NAME, token.refreshToken())
            .maxAge(3600)
            .httpOnly(true)
            .path("/")
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
            .body(ApiResponse.success(new ReIssueTokenResponse(token.accessToken())));
    }
}
