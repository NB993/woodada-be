package com.woodada.common.auth.adapter.out.external;

import com.woodada.common.auth.adapter.out.external.response.GoogleUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "GoogleUserInfoFeignClient",
    url = "https://www.googleapis.com/oauth2/v1/userinfo"
)
public interface GoogleUserInfoFeignClient {

    @GetMapping
    GoogleUserInfoResponse requestUserInfo(@RequestParam(name = "access_token") String accessToken);
}
