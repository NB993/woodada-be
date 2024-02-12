package com.woodada.common.auth.adapter.out.persistence;

import com.woodada.common.auth.application.port.out.RefreshTokenSavePort;
import com.woodada.common.auth.domain.Token;
import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenAdapter implements RefreshTokenSavePort {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenAdapter(final StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void save(final Long memberId, final String refreshToken) {
        final String key = String.format("member::%d::string::refresh_token", memberId);

        try {
            stringRedisTemplate.opsForSet().add(key, refreshToken);
            stringRedisTemplate.expire(key, Duration.ofSeconds(Token.REFRESH_TOKEN_EXPIRATION_PERIOD));
        } catch(Exception e) {
            //todo 레디스 저장 도중 발생 가능한 예외 종류와 그에 대한 처리?
            throw new RuntimeException(e);
        }
    }
}
