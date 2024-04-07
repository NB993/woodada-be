package com.woodada.core.diary.adapter.in.response;

import java.time.LocalDateTime;

public record DiaryQueryResponse(
    Long id,
    String title,
    String contents,
    LocalDateTime createdAt
) {

}
