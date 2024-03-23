package com.woodada.common.config.web;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("interceptor")
public class InterceptorProperties {

    private final Map<String, List<String>> addPatterns;
    private final Map<String, List<String>> excludePatterns;

    public InterceptorProperties(final Map<String, List<String>> addPatterns, final Map<String, List<String>> excludePatterns) {
        this.addPatterns = addPatterns;
        this.excludePatterns = excludePatterns;
    }

    public String[] getAuthExcludePatterns() {
        final List<String> authExcludePatterns = excludePatterns.get("auth");
        return authExcludePatterns.toArray(String[]::new);
    }


    public String[] getAuthAddPatterns() {
        final List<String> authAddPatterns = addPatterns.get("auth");
        return authAddPatterns.toArray(String[]::new);
    }
}
