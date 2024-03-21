package com.woodada.core.diary.adapter.in.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WriteDiaryRequest {

    @NotBlank
    private String contents;
}
