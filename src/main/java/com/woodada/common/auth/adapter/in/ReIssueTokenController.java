package com.woodada.common.auth.adapter.in;


import com.woodada.common.auth.adapter.in.response.ReIssueTokenResponse;
import com.woodada.common.support.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class ReIssueTokenController {

    @PostMapping
    ResponseEntity<ApiResponse<ReIssueTokenResponse>> reIssueToken() {

        return ResponseEntity.ok()
            .body(ApiResponse.success(new ReIssueTokenResponse("access_token")));
    }
}
