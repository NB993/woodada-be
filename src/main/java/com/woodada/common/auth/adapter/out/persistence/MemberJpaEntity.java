package com.woodada.common.auth.adapter.out.persistence;

import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.Member;
import com.woodada.common.auth.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class MemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_url")
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "deleted")
    private Deleted deleted;

    private MemberJpaEntity(final Long id, final String email, final String name, final String profileUrl, final UserRole role, final Deleted deleted) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = role;
        this.deleted = deleted;
    }

    /**
     * DomainEntity to JpaEntity 매핑
     * @param member 도메인 Member 엔티티
     * @return JPA Member 엔티티
     */
    public static MemberJpaEntity of(final Member member) {
        return new MemberJpaEntity(
            member.getId(),
            member.getEmail(),
            member.getName(),
            member.getProfileUrl(),
            member.getRole(),
            member.getDeleted()
        );
    }

    /**
     * JpaEntity to DomainEntity 매핑
     * @return 도메인 Member 엔티티
     */
    public Member toDomainEntity() {
        return Member.withId(
            this.id,
            this.email,
            this.name,
            this.profileUrl,
            this.role,
            this.deleted
        );
    }
}
