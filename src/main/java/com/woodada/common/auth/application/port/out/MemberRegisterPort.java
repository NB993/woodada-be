package com.woodada.common.auth.application.port.out;

import com.woodada.common.auth.domain.Member;

public interface MemberRegisterPort {

    Member register(Member member);
}
