package com.woodada.core.member.in.response;

import com.woodada.common.auth.domain.UserRole;

public record MeResponse(
    Long id,
    String email,
    String name,
    String profileUrl,
    UserRole userRole
) {

}
