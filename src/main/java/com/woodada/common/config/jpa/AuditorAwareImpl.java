package com.woodada.common.config.jpa;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<Long> {

    private Long memberId = 0L;

    @Override
    public Optional<Long> getCurrentAuditor() {
        //todo spring security
        return Optional.of(memberId);
    }

    //todo 다이어리 crud 끝내고 시큐리티 적용..
    public void setCurrentAuditor(Long memberId) {
        this.memberId = memberId;
    }
}
