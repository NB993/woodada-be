package com.woodada.common.auth.argument_resolver;

import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.Member;
import com.woodada.common.auth.domain.UserRole;
import org.junit.jupiter.api.DisplayName;

@DisplayName("[Helper] Member 관련 헬퍼")
public class MemberHelper {

    public static WddMember createWddMember(
        final Long id,
        final String email,
        final String name
    ) {
        return new WddMember(id, email, name);
    }

    public static Member createMember(
        final Long id,
        final String email,
        final String name,
        final String profileUrl,
        final UserRole userRole,
        final Deleted deleted
    ) {
        return Member.withId(id, email, name, profileUrl, userRole, deleted);
    }

    public static MemberJpaEntity createMemberJpaEntity(
        final Long id,
        final String email,
        final String name,
        final String profileUrl,
        final UserRole userRole,
        final Deleted deleted
    ) {
        final Member member = createMember(id, email, name, profileUrl, userRole, deleted);

        return MemberJpaEntity.of(member);
    }
}
