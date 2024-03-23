package com.woodada.core.diary.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.woodada.core.diary.application.port.in.WriteDiaryCommand;
import com.woodada.core.diary.application.port.out.SaveDiaryPort;
import com.woodada.core.diary.domain.Diary;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("[unit test] WriteDiaryUseCase 단위테스트")
class WriteDiaryServiceTest {

    @Mock
    private SaveDiaryPort saveDiaryPort;

    private WriteDiaryService writeDiaryService;

    @BeforeEach
    void setUp() {
        writeDiaryService = new WriteDiaryService(saveDiaryPort);
    }

    @DisplayName("유효한 글 작성 모델을 전달받으면 작성에 성공한다.")
    @Test
    void given_valid_command_then_diary_is_wrote() {
        //given
        WriteDiaryCommand writeDiaryCommand = new WriteDiaryCommand("100자 이하의 제목", "5000자 이하의 본문", LocalDateTime.now());
        given(saveDiaryPort.saveDiary(any(Diary.class)))
            .willReturn(Diary.withId(1L, "100자 이하의 제목", "5000자 이하의 본문"));

        //when
        Diary savedDiary = writeDiaryService.writeDiary(writeDiaryCommand);

        //then
        assertThat(savedDiary).isNotNull();
        then(saveDiaryPort).should(times(1)).saveDiary(any(Diary.class));
    }
}
