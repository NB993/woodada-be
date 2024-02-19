package com.woodada.core.member.application.port.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.core.member.domain.WddMe;
import org.springframework.stereotype.Service;

@Service
public class GetMeService implements GetMeUseCase {

    @Override
    public WddMe getMe(WddMember wddMember) {
        return null;
    }
}
