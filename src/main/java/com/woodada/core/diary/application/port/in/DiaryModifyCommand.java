package com.woodada.core.diary.application.port.in;

import org.springframework.util.ObjectUtils;

public record DiaryModifyCommand(
    Long id,
    String title,
    String contents
) {

    public DiaryModifyCommand {
        if (ObjectUtils.isEmpty(id)) {
            throw new IllegalArgumentException("수정할 일기 id를 입력해 주세요.");
        }

        if (ObjectUtils.isEmpty(title) || title.length() < 1) {
            throw new IllegalArgumentException("일기 제목을 한 글자 이상 입력해 주세요.");
        }

        if (ObjectUtils.isEmpty(contents) || contents.length() < 1) {
            throw new IllegalArgumentException("일기 본문을 한 글자 이상 입력해 주세요.");
        }
    }
}
