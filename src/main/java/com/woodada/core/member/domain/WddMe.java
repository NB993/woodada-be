package com.woodada.core.member.domain;

import com.woodada.common.auth.domain.UserRole;

public record WddMe(
    Long id,
    String email,
    String name,
    String profileUrl,
    UserRole role
) {

}
