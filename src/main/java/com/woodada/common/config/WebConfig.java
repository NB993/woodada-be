package com.woodada.common.config;

import com.woodada.common.auth.argument_resolver.WddMemberResolver;
import com.woodada.common.auth.interceptor.AuthInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;
    private final InterceptorProperties interceptorProperties;
    private final AuthInterceptor authInterceptor;
    private final WddMemberResolver wddMemberResolver;

    public WebConfig(
        final CorsProperties corsProperties,
        final InterceptorProperties interceptorProperties,
        final AuthInterceptor authInterceptor,
        final WddMemberResolver wddMemberResolver
    ) {
        this.corsProperties = corsProperties;
        this.interceptorProperties = interceptorProperties;
        this.authInterceptor = authInterceptor;
        this.wddMemberResolver = wddMemberResolver;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final List<String> origins = corsProperties.getOrigins();

        registry.addMapping("/**")
            .allowedOrigins(origins.toArray(String[]::new))
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns(interceptorProperties.getAuthAddPatterns())
            .excludePathPatterns(interceptorProperties.getAuthExcludePatterns());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(wddMemberResolver);
    }
}
