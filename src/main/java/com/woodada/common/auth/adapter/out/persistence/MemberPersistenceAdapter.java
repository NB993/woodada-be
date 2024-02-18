package com.woodada.common.auth.adapter.out.persistence;

import com.woodada.common.auth.application.port.out.MemberQueryPort;
import com.woodada.common.auth.application.port.out.MemberRegisterPort;
import com.woodada.common.auth.domain.Member;
import com.woodada.common.auth.domain.OAuth2UserInfo;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class MemberPersistenceAdapter implements MemberQueryPort, MemberRegisterPort {

    private final MemberRepository memberRepository;

    public MemberPersistenceAdapter(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> findMember(final OAuth2UserInfo userInfo) {
        final Optional<MemberJpaEntity> optionalMember = memberRepository.findByEmail(userInfo.email());
        if (optionalMember.isEmpty()) {
            return Optional.empty();
        }

        final MemberJpaEntity foundMember = optionalMember.get();
        return Optional.ofNullable(foundMember.toDomainEntity());
    }

    @Override
    public Member register(final Member member) {
        final MemberJpaEntity newMember = MemberJpaEntity.of(member);
        final MemberJpaEntity savedMember = memberRepository.save(newMember);

        return Member.withId(
            savedMember.getId(),
            savedMember.getEmail(),
            savedMember.getName(),
            savedMember.getProfileUrl(),
            savedMember.getRole(),
            savedMember.getDeleted()
        );
    }
}
