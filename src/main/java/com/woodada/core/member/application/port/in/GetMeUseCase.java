package com.woodada.core.member.application.port.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.core.member.domain.WddMe;

public interface GetMeUseCase {

    WddMe getMe(WddMember wddMember);
}
