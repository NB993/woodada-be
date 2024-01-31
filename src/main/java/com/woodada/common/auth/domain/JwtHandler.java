package com.woodada.common.auth.domain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtHandler {

    public String createToken(final String email, final long expiration, final Instant issueDate) {
        return Jwts.builder()
            .header()
            .type("JWT")
            .and()
            .issuer("issuer")
            .claim("email", email)
            .issuedAt(Date.from(issueDate))
            .expiration(Date.from(issueDate.plusSeconds(expiration)))
            .signWith(Keys.hmacShaKeyFor("K".repeat(32).getBytes(StandardCharsets.UTF_8)))
            .compact();
    }
}
