package com.woodada.common.auth.domain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtHandler {

    private final JwtProperties jwtProperties;

    public JwtHandler(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String createToken(final Long memberId, final long expiration, final Instant issueDate) {
        return Jwts.builder()
            .header()
            .type(jwtProperties.type())
            .and()
            .issuer(jwtProperties.issuer())
            .claim(jwtProperties.memberIdentifier(), memberId)
            .issuedAt(Date.from(issueDate))
            .expiration(Date.from(issueDate.plusSeconds(expiration)))
            .signWith(createSecretKey())
            .compact();
    }

    private SecretKey createSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8));
    }
}
