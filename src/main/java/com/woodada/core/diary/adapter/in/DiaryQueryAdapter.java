package com.woodada.core.diary.adapter.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.diary.adapter.in.response.DiaryQueryResponse;
import com.woodada.core.diary.application.port.in.DiaryQueryCommand;
import com.woodada.core.diary.application.port.in.DiaryQueryUseCase;
import com.woodada.core.diary.domain.Diary;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    ResponseEntity<ApiResponse<List<DiaryQueryResponse>>> queryDiaries(
        WddMember wddMember,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        final DiaryQueryCommand command = new DiaryQueryCommand(startDate, endDate);
        final List<Diary> diaries = diaryQueryUseCase.queryDiaries(wddMember, command);
        final List<DiaryQueryResponse> response = diaries.stream()
            .map(DiaryQueryResponse::from)
            .collect(Collectors.toList());

        final ApiResponse<List<DiaryQueryResponse>> apiResponse = ApiResponse.success(response);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<DiaryQueryResponse>> queryDiary(WddMember wddMember, @PathVariable Long id) {
        final Diary diary = diaryQueryUseCase.queryDiary(wddMember, id);
        final DiaryQueryResponse response = DiaryQueryResponse.from(diary);
        final ApiResponse<DiaryQueryResponse> apiResponse = ApiResponse.success(response);

        return ResponseEntity.ok(apiResponse);
    }
}
