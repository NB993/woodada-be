package com.woodada.core.diary.adapter.in.response;

import com.woodada.core.diary.domain.Diary;
import java.time.LocalDateTime;

public record DiaryQueryResponse(
    Long id,
    String title,
    String contents,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {

    public static DiaryQueryResponse from(final Diary diary) {
        return new DiaryQueryResponse(diary.getId(), diary.getTitle(), diary.getContents(), diary.getCreatedAt(), diary.getModifiedAt());
    }
}
