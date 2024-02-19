package com.woodada.core.member.adapter.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.member.adapter.in.response.MeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/member")
@RestController
public class MemberAdapter {

    @GetMapping("/me")
    ResponseEntity<ApiResponse<MeResponse>> getMe(final WddMember wddMember) {
        final MeResponse meResponse = new MeResponse(wddMember.getId(), wddMember.getEmail(), wddMember.getName(), wddMember.getProfileUrl(), wddMember.getRole());

        final ApiResponse<MeResponse> response = ApiResponse.success(meResponse);
        return ResponseEntity.ok()
            .body(response);
    }
}
