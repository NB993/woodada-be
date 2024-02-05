package com.woodada.common.auth.domain;

import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private String email;
    private String name;
    private String profileUrl;
    private UserRole role;
    private Deleted deleted;

    private Member(final Long id, final String email, final String name, final String profileUrl, final UserRole role, final Deleted deleted) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = role;
        this.deleted = deleted;
    }

    /**
     * 가입할 멤버 정보를 생성
     * @param email
     * @param name
     * @param profileUrl
     * @param userRole
     * @return 도메인 Member 엔티티
     */
    public static Member withoutId(
        final String email,
        final String name,
        final String profileUrl,
        final UserRole userRole
    ) {
        return new Member(null, email, name, profileUrl, userRole, Deleted.FALSE);
    }

    /**
     * 가입된 멤버를 반환
     * @param id
     * @param email
     * @param name
     * @param profileUrl
     * @param userRole
     * @param deleted
     * @return 도메인 Member 엔티티
     */
    public static Member withId(
        final Long id,
        final String email,
        final String name,
        final String profileUrl,
        final UserRole userRole,
        final Deleted deleted
    ) {
        return new Member(id, email, name, profileUrl, userRole, deleted);
    }
}
