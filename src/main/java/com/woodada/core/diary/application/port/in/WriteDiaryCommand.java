package com.woodada.core.diary.application.port.in;

import java.time.LocalDateTime;

public record WriteDiaryCommand(
    String title,
    String contents,
    LocalDateTime writeDateTime
) {
}
