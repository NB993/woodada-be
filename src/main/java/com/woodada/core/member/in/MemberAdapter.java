package com.woodada.core.member.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.member.in.response.MeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/member")
@RestController
public class MemberAdapter {

    @GetMapping("/me")
    ResponseEntity<ApiResponse<MeResponse>> getMe(final WddMember wddMember) {
        MeResponse meResponse = new MeResponse(1L, "email", "name", "profile_url", UserRole.NORMAL);

        final ApiResponse<MeResponse> response = ApiResponse.success(meResponse);
        return ResponseEntity.ok()
            .body(response);
    }
}
