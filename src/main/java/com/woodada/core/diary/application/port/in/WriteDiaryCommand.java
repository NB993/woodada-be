package com.woodada.core.diary.application.port.in;

import java.time.LocalDate;

public record WriteDiaryCommand(
    String title,
    String contents,
    LocalDate writeDate
) {
}
