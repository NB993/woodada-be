package com.woodada.core.member.adapter.in;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.member.adapter.in.response.MeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DisplayName("[unit test] MemberAdapter 단위 테스트")
class MeAdapterTest {

    private MeAdapter meAdapter;

    @BeforeEach
    void setUp() {
        meAdapter = new MeAdapter();
    }

    @DisplayName("getMe 핸들러에 WddMember를 전달받으면 MeResponse를 리턴한다.")
    @Test
    void when_get_wdd_member_then_return_me_response() {
        //given
        final WddMember wddMember = getWddMember();

        //when
        ResponseEntity<ApiResponse<MeResponse>> responseEntity = meAdapter.getMe(wddMember);

        //then
        final ApiResponse<MeResponse> body = responseEntity.getBody();
        final MeResponse data = body.getData();

        Assertions.assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(body.isSuccess()).isTrue(),
            () -> assertThat(data.id()).isEqualTo(1L),
            () -> assertThat(data.email()).isEqualTo("test@email.com"),
            () -> assertThat(data.name()).isEqualTo("테스트유저"),
            () -> assertThat(data.profileUrl()).isEqualTo("test_profile_url"),
            () -> assertThat(data.userRole()).isEqualTo(UserRole.NORMAL)
        );
    }

    private static WddMember getWddMember() {
        return MemberHelper.createWddMember(
            1L,
            "test@email.com",
            "테스트유저",
            "test_profile_url",
            UserRole.NORMAL
        );
    }
}
