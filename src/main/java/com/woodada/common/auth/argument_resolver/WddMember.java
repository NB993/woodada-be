package com.woodada.common.auth.argument_resolver;

import com.woodada.common.auth.domain.UserRole;
import lombok.Getter;

/**
 * 인증이 완료된 멤버 정보
 */
@Getter
public class WddMember {

    private final Long id;
    private final String email;
    private final String name;
    private final String profileUrl;
    private final UserRole role;
    //todo 추가적으로 필요한 정보?

    /**
     * 인증된 멤버 정보는 WddMemberResolver만 생성할 수 있게 접근 제한
     */
    WddMember(final Long id, final String email, final String name, final String profileUrl, final UserRole role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = role;
    }
}
