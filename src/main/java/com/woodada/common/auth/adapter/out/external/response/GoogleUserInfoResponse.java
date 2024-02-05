package com.woodada.common.auth.adapter.out.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woodada.common.auth.domain.OAuth2UserInfo;

public record GoogleUserInfoResponse(
    @JsonProperty("email") String email,
    @JsonProperty("name") String name,
    @JsonProperty("picture") String picture,

    @JsonProperty("id") String id,
    @JsonProperty("verified_email") Boolean verifiedEmail,
    @JsonProperty("given_name") String givenName,
    @JsonProperty("family_name") String familyName,
    @JsonProperty("locale") String locale
){

    public OAuth2UserInfo toOAuth2UserInfo() {
        return new OAuth2UserInfo(
            this.email,
            this.name,
            this.picture
        );
    }
}
