package com.woodada.common.auth.domain;

public record OAuth2AccessToken(
    String accessToken,
    String tokenType
){

}
