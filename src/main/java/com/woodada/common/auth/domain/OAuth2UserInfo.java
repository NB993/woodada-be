package com.woodada.common.auth.domain;

public record OAuth2UserInfo(
    String email,
    String name,
    String profileUrl
){

}
