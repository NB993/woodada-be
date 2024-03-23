package com.woodada.common.config.jpa;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        //todo spring security
        return Optional.of(0L);
    }
}
