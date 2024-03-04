package com.woodada.common.auth.application.service;

import com.woodada.common.auth.application.port.in.ReIssueTokenUseCase;
import com.woodada.common.auth.domain.Token;
import org.springframework.stereotype.Service;

@Service
public class ReIssueTokenService implements ReIssueTokenUseCase {

    @Override
    public Token reIssue(Long memberId) {
        return new Token("re_issued_access_token", "re_issued_refresh_token");
    }
}
