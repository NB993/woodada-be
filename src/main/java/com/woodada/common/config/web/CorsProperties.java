package com.woodada.common.config.web;

import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("cors")
public class CorsProperties {

    private final List<String> origins;

    public CorsProperties(final List<String> origins) {
        this.origins = origins;
    }
}
