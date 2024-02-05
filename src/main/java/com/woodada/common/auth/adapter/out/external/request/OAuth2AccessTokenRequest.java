package com.woodada.common.auth.adapter.out.external.request;

public record OAuth2AccessTokenRequest(
    String code,
    String clientId,
    String clientSecret,
    String grantType,
    String redirectUri
) {

}
