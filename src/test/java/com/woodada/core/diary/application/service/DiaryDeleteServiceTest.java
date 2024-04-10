package com.woodada.core.diary.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.core.diary.application.port.out.DiaryFindPort;
import com.woodada.core.diary.application.port.out.DiarySavePort;
import com.woodada.core.diary.domain.Diary;
import com.woodada.support.Deleted;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("[unit test] DiaryDeleteService 단위테스트")
class DiaryDeleteServiceTest {

    @Mock private DiaryFindPort diaryFindPort;
    @Mock private DiarySavePort diarySavePort;

    private DiaryDeleteService diaryDeleteService;

    @BeforeEach
    void setUp() {
        diaryDeleteService = new DiaryDeleteService(diaryFindPort, diarySavePort);
    }

    @DisplayName("[deleteDiary] - wddMember가 null인 경우 예외가 발생한다.")
    @Test
    void given_wdd_member_is_null_when_delete_diary_then_throw_exception() {
        assertThatThrownBy(() -> diaryDeleteService.deleteDiary(null, 1L))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[deleteDiary] - id가 null인 경우 예외가 발생한다.")
    @Test
    void given_id_is_null_when_delete_diary_then_throw_exception() {
        WddMember wddMember = getWddMember(1L);

        assertThatThrownBy(() -> diaryDeleteService.deleteDiary(wddMember, null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[deleteDiary] - 모든 인자가 유효한 경우 일기 삭제에 성공한다.")
    @Test
    void given_all_args_are_valid_when_delete_diary_then_delete_success() {
        //given
        WddMember wddMember = getWddMember(1L);
        Diary foundDiary = Diary.withId(1L, "제목", "본문", Deleted.FALSE, 1L, LocalDateTime.now(), 1L, LocalDateTime.now());
        given(diaryFindPort.findDiary(1L, 1L))
            .willReturn(Optional.of(foundDiary));

        //when
        diaryDeleteService.deleteDiary(wddMember, 1L);

        //then
        assertThat(foundDiary.getDeleted()).isEqualTo(Deleted.TRUE);
        then(diarySavePort).should(times(1)).saveDiary(any(Diary.class));
    }

    private WddMember getWddMember(Long memberId) {
        WddMember writer = MemberHelper.createWddMember(memberId, "test@email.com", "테스트유저", "test_protile_url", UserRole.NORMAL);
        return writer;
    }
}
