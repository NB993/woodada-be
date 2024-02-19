package com.woodada.common.auth.argument_resolver;

import org.junit.jupiter.api.DisplayName;

@DisplayName("[Helper] WddMember 테스트 헬퍼")
public class WddMemberHelper {

    public static WddMember create(final Long id, final String email, final String name) {
        return new WddMember(id, email, name);
    }
}
