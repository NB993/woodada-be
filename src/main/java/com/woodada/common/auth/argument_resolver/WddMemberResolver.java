package com.woodada.common.auth.argument_resolver;

import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class WddMemberResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    public WddMemberResolver(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final Class<?> parameterType = parameter.getParameterType();
        return WddMember.class.isAssignableFrom(parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final Long memberId = (Long) request.getAttribute("memberId");

        final Optional<MemberJpaEntity> optionalMember = memberRepository.findByIdAndDeleted(memberId, Deleted.FALSE);
        if (optionalMember.isEmpty()) {
            log.error("[WddMemberResolver][resolveArgument] memberId = [{}]", memberId);
            throw new AuthenticationException("유저 조회 실패");
        }

        final MemberJpaEntity authMember = optionalMember.get();
        return new WddMember(
            authMember.getId(),
            authMember.getEmail(),
            authMember.getName(),
            authMember.getProfileUrl(),
            authMember.getRole()
        );
    }
}
