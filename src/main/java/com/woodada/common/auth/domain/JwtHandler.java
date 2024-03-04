package com.woodada.common.auth.domain;

import com.woodada.common.auth.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtHandler {

    private final int TOKEN_INDEX = 1;

    private final JwtProperties jwtProperties;

    public JwtHandler(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String createToken(final Long memberId, final long expiration, final Instant issueDate) {
        // todo 유효하지 않은 인자에 대한 예외 처리? 유효하지 않은 인자가 넘어오기 전 단계에서 예외를 발생시켜야?
        if (ObjectUtils.isEmpty(memberId) || memberId < 1) {
            throw new RuntimeException("음");
        }

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

    public Claims decode(final String authHeader) {
        final String token = extractToken(authHeader);
        return Jwts.parser()
            .setSigningKey(createSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Long decodeTokenWithHeader(final String authHeader) {
        final String token = extractToken(authHeader);
        return extractMemberId(token);
    }

    public Long decodeToken(final String refreshToken) {
        return extractMemberId(refreshToken);
    }

    private SecretKey createSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8));
    }

    private String extractToken(final String authHeader) {
        if (authHeader.contains(jwtProperties.authScheme())) {
            return authHeader.split(jwtProperties.authScheme())[TOKEN_INDEX];
        }
        throw new AuthenticationException("invalid auth header scheme");
    }

    private Long extractMemberId(final String token) {
        final Claims claims = Jwts.parser()
            .setSigningKey(createSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.get(jwtProperties.memberIdentifier(), Long.class);
    }
}
