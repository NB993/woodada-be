package com.woodada.common.auth.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String type,
    String issuer,
    String memberIdentifier,
    String secretKey
) {

}
