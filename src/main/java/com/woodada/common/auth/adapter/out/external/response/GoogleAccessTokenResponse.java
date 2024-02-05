package com.woodada.common.auth.adapter.out.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woodada.common.auth.domain.OAuth2AccessToken;

public record GoogleAccessTokenResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("expires_in") Integer expiresIn,
    @JsonProperty("token_type") String tokeType,
    @JsonProperty("refresh_token") String refreshToken
){

    public OAuth2AccessToken toOAuth2AccessToken() {
        return new OAuth2AccessToken(this.accessToken, this.tokeType);
    }
}
