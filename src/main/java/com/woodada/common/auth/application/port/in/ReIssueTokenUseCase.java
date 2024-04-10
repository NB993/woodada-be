package com.woodada.common.auth.application.port.in;

import com.woodada.common.auth.domain.Token;
import java.time.Instant;

public interface ReIssueTokenUseCase {

    Token reIssue(Long memberId, Instant issuedAt);
}
