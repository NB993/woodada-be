package com.woodada.core.diary.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.core.diary.application.port.in.DiaryQueryCommand;
import com.woodada.core.diary.application.port.out.DiaryFindPort;
import com.woodada.core.diary.domain.Diary;
import com.woodada.support.Deleted;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("[unit test] DiaryQueryService 단위테스트")
public class DiaryQueryServiceTest {

    @Mock
    private DiaryFindPort findDiaryPort;

    private DiaryQueryService diaryQueryService;

    @BeforeEach
    void setUp() {
        diaryQueryService = new DiaryQueryService(findDiaryPort);
    }

    @DisplayName("[queryDiaries] - wddMember가 null인 경우 예외가 발생한다.")
    @Test
    void given_wdd_member_is_null_when_query_diaries_then_throw_exception() {
        DiaryQueryCommand command = new DiaryQueryCommand(LocalDate.now(), LocalDate.now().plusDays(30));

        assertThatThrownBy(() -> diaryQueryService.queryDiaries(null, command))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[queryDiaries] - command가 null인 경우 예외가 발생한다.")
    @Test
    void given_command_is_null_when_query_diaries__then_throw_exception() {
        WddMember wddMember = getWddMember(1L);

        assertThatThrownBy(() -> diaryQueryService.queryDiaries(wddMember, null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[queryDiaries] - 모든 인자가 유효한 경우 일기 목록 조회에 성공한다.")
    @Test
    void given_all_args_are_not_null_when_query_diaries_then_query_success() {
        //given
        WddMember wddMember = getWddMember(1L);
        DiaryQueryCommand command = new DiaryQueryCommand(LocalDate.now(), LocalDate.now().plusDays(30));
        given(findDiaryPort.findAll(wddMember.getId(), command.startDate(), command.endDate()))
            .willReturn(List.of(Diary.withId(1L, "제목", "본문", Deleted.FALSE, 1L, LocalDateTime.now(), 1L, LocalDateTime.now())));

        //when
        List<Diary> diaries = diaryQueryService.queryDiaries(wddMember, command);

        //then
        assertThat(diaries.size()).isEqualTo(1);
        assertThat(diaries.get(0).getId()).isEqualTo(1L);
    }

    @DisplayName("[queryDiary] - wddMember가 null일 경우 예외가 발생한다.")
    @Test
    void given_wdd_member_is_null_when_query_diary_then_throw_exception() {
        assertThatThrownBy(() -> diaryQueryService.queryDiary(null, 1L))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[queryDiary] - id가 null일 경우 예외가 발생한다.")
    @Test
    void given_id_is_null_when_query_diary__then_throw_exception() {
        WddMember wddMember = getWddMember(1L);

        assertThatThrownBy(() -> diaryQueryService.queryDiary(wddMember, null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[queryDiary] - 모든 인자가 유효한 경우 일기 조회에 성공한다.")
    @Test
    void given_all_args_are_not_null_when_query_diary_then_query_success() {
        //given
        WddMember wddMember = getWddMember(1L);
        given(findDiaryPort.findDiary(1L, 1L))
            .willReturn(Optional.of(Diary.withId(1L, "제목", "본문", Deleted.FALSE, 1L, LocalDateTime.now(), 1L, LocalDateTime.now())));

        //when
        Diary diary = diaryQueryService.queryDiary(wddMember, 1L);

        //then
        assertThat(diary.getId()).isEqualTo(1L);
        assertThat(diary.getTitle()).isEqualTo("제목");
        assertThat(diary.getContents()).isEqualTo("본문");
        assertThat(diary.getCreatedBy()).isEqualTo(1L);
    }


    private WddMember getWddMember(Long memberId) {
        WddMember writer = MemberHelper.createWddMember(memberId, "test@email.com", "테스트유저", "test_protile_url", UserRole.NORMAL);
        return writer;
    }
}
