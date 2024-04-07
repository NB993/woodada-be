package com.woodada.core.diary.adapter.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.diary.adapter.in.response.DiaryQueryResponse;
import com.woodada.core.diary.application.port.in.DiaryQueryCommand;
import com.woodada.core.diary.application.port.in.DiaryQueryUseCase;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryQueryAdapter {

    private final DiaryQueryUseCase diaryQueryUseCase;

    public DiaryQueryAdapter(final DiaryQueryUseCase diaryQueryUseCase) {
        this.diaryQueryUseCase = diaryQueryUseCase;
    }

    ResponseEntity<ApiResponse<DiaryQueryResponse>> queryDiaries(
        WddMember wddMember,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        final DiaryQueryCommand command = new DiaryQueryCommand(startDate, endDate);

        return null;
    }
}
