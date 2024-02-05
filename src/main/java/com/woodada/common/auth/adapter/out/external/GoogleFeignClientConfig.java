package com.woodada.common.auth.adapter.out.external;

import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class GoogleFeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header(HttpHeaders.CONTENT_LENGTH, "0");
            template.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
        };
    }

    @Bean
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(() -> new HttpMessageConverters(new RestTemplate().getMessageConverters())));
    }
}
