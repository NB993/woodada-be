package com.woodada.core.member.adapter.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.member.adapter.in.response.MeResponse;
import com.woodada.core.member.application.port.in.GetMeUseCase;
import com.woodada.core.member.domain.WddMe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/member")
@RestController
public class MemberAdapter {

    private final GetMeUseCase getMeUseCase;

    public MemberAdapter(final GetMeUseCase getMeUseCase) {
        this.getMeUseCase = getMeUseCase;
    }

    @GetMapping("/me")
    ResponseEntity<ApiResponse<MeResponse>> getMe(final WddMember wddMember) {
        final WddMe wddMe = getMeUseCase.getMe(wddMember);
        final MeResponse meResponse = new MeResponse(wddMe.id(), wddMe.email(), wddMe.name(), wddMe.profileUrl(), wddMe.role());

        final ApiResponse<MeResponse> response = ApiResponse.success(meResponse);
        return ResponseEntity.ok()
            .body(response);
    }
}
