package com.woodada.common.auth.argument_resolver;

import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.exception.AuthenticationException;
import com.woodada.common.config.jpa.AuditorAwareImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class WddMemberResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;
    private final AuditorAwareImpl auditorAware;

    public WddMemberResolver(final MemberRepository memberRepository, final AuditorAware auditorAware) {
        this.memberRepository = memberRepository;
        this.auditorAware = (AuditorAwareImpl) auditorAware;
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
            throw new AuthenticationException("NOT_FOUND_MEMBER");
        }

        final MemberJpaEntity authMember = optionalMember.get();

        //todo 다이어리 crud 끝나고 시큐리티 적용한 후에 제거..
        auditorAware.setCurrentAuditor(authMember.getId());

        return new WddMember(
            authMember.getId(),
            authMember.getEmail(),
            authMember.getName(),
            authMember.getProfileUrl(),
            authMember.getRole()
        );
    }
}
