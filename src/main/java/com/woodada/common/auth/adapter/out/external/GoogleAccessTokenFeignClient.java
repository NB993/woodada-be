package com.woodada.common.auth.adapter.out.external;

import com.woodada.common.auth.adapter.out.external.response.GoogleAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "GoogleAccessTokenFeignClient",
    url = "https://oauth2.googleapis.com/token",
    configuration = GoogleFeignClientConfig.class
)
public interface GoogleAccessTokenFeignClient {

    @PostMapping
    GoogleAccessTokenResponse requestToken(
        @RequestParam(name = "code") String code,
        @RequestParam(name = "client_id") String clientId,
        @RequestParam(name = "client_secret") String clientSecret,
        @RequestParam(name = "redirect_uri") String redirectUri,
        @RequestParam(name = "grant_type") String grantType
    );
}
