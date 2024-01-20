package com.woodada.common.auth.adapter.in;

import com.woodada.common.auth.adapter.in.response.AccessTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth2")
public class OAuth2LoginController {

    @GetMapping("/{providerType}")
    ResponseEntity<AccessTokenResponse> callBack(
        @PathVariable("providerType") String providerType,
        @RequestParam("code") String code
    ) {
        //todo 인증, 토큰발급 개발
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", "refresh_token")
            .maxAge(1800)
            .httpOnly(true)
            .path("/")
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
            .body(new AccessTokenResponse("access_token"));
    }
}
