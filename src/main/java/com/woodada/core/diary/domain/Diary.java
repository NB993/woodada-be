package com.woodada.core.diary.domain;

import lombok.Getter;

@Getter
public class Diary {

    private final Long id;
    private final String title;
    private final String contents;

    private Diary(final Long id, final String title, final String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    /**
     * DB 저장 전 일기 인스턴스를 생성한다
     *
     * @param title 제목
     * @param contents 본문
     * @return 저장되지 않은 Diary
     */
    public static Diary withoutId(final String title, final String contents) {
        return new Diary(null, title, contents);
    }

    /**
     * DB에서 조회된 일기 인스턴스를 생성한다
     *
     * @param id diary id
     * @param title 제목
     * @param contents 본문
     * @return 조회된 Diary
     */
    public static Diary withId(final Long id, final String title, final String contents) {
        return new Diary(id, title, contents);
    }
}
