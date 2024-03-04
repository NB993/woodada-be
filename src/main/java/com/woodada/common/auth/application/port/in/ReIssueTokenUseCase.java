package com.woodada.common.auth.application.port.in;

import com.woodada.common.auth.domain.Token;

public interface ReIssueTokenUseCase {

    Token reIssue(Long memberId);
}
