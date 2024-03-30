package com.woodada.core.diary.domain;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public class Diary {

    private final Long id;
    private String title;
    private String contents;

    private Diary(final Long id, final String title, final String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    /**
     * DB 저장 전 일기 인스턴스를 생성한다
     *
     * @param title    제목
     * @param contents 본문
     * @return 저장되지 않은 Diary
     * @throws IllegalArgumentException 생성할 일기 제목, 본문을 null 또는 한 글자 미만으로 입력한 경우
     */
    public static Diary withoutId(final String title, final String contents) {
        if (ObjectUtils.isEmpty(title) || title.length() < 1) {
            throw new IllegalArgumentException("일기 제목을 한 글자 이상 입력해 주세요.");
        }

        if (ObjectUtils.isEmpty(contents) || contents.length() < 1) {
            throw new IllegalArgumentException("일기 본문을 한 글자 이상 입력해 주세요.");
        }

        return new Diary(null, title, contents);
    }

    /**
     * DB에서 조회된 일기 인스턴스를 생성한다
     *
     * @param id       diary id
     * @param title    제목
     * @param contents 본문
     * @return 조회된 Diary
     */
    public static Diary withId(final Long id, final String title, final String contents) {
        return new Diary(id, title, contents);
    }

    /**
     * 일기를 수정한다
     *
     * @param title    제목
     * @param contents 본문
     * @throws IllegalArgumentException 수정할 일기 제목, 본문을 null 또는 한 글자 미만으로 입력한 경우
     */
    public void modify(final String title, final String contents) {
        if (ObjectUtils.isEmpty(title) || title.length() < 1) {
            throw new IllegalArgumentException("수정할 제목을 한 글자 이상 입력해 주세요.");
        }

        if (ObjectUtils.isEmpty(contents) || contents.length() < 1) {
            throw new IllegalArgumentException("수정할 본문을 한 글자 이상 입력해 주세요.");
        }

        this.title = title;
        this.contents = contents;
    }
}
