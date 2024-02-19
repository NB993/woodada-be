package com.woodada.core.member.in;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.auth.argument_resolver.WddMemberHelper;
import com.woodada.common.support.ApiResponse;
import com.woodada.common.support.ResultCode;
import com.woodada.core.member.in.response.MeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DisplayName("[unit test] MemberAdapter 단위 테스트")
class MemberAdapterTest {

    private MemberAdapter memberAdapter;

    @BeforeEach
    void setUp() {
        memberAdapter = new MemberAdapter();
    }

    @DisplayName("getMe() 테스트")
    @Test
    void getMe() {
        //given
        final WddMember wddMember = WddMemberHelper.create(1L, "test@email.com", "테스트유저");

        //when
        ResponseEntity<ApiResponse<MeResponse>> responseEntity = memberAdapter.getMe(wddMember);


        //then
        final ApiResponse<MeResponse> body = responseEntity.getBody();
        final MeResponse data = body.getData();

        Assertions.assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(body.getResult()).isEqualTo(ResultCode.SUCCESS),
            () -> assertThat(data.id()).isEqualTo(1L),
            () -> assertThat(data.email()).isEqualTo("test@email.com"),
            () -> assertThat(data.name()).isEqualTo("테스트유저")
        );
    }
}
