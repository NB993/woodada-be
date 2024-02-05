package com.woodada.common.auth.application.port.out;

import com.woodada.common.auth.domain.Member;
import com.woodada.common.auth.domain.OAuth2UserInfo;
import java.util.Optional;

public interface MemberQueryPort {

    Optional<Member> findMember(OAuth2UserInfo userInfo);
}
