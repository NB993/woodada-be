package com.woodada.core.diary.adapter.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.diary.adapter.in.request.DiaryWriteRequest;
import com.woodada.core.diary.application.port.in.DiaryWriteCommand;
import com.woodada.core.diary.application.port.in.DiaryWriteUseCase;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryWriteAdapter {

    private final DiaryWriteUseCase diaryWriteUseCase;

    public DiaryWriteAdapter(final DiaryWriteUseCase diaryWriteUseCase) {
        this.diaryWriteUseCase = diaryWriteUseCase;
    }

    @PostMapping
    ResponseEntity<ApiResponse<Void>> writeDiary(WddMember wddMember, @RequestBody @Valid DiaryWriteRequest request) {
        final DiaryWriteCommand command = new DiaryWriteCommand(request.getTitle(), request.getContents(), LocalDate.now());
        diaryWriteUseCase.writeDiary(wddMember, command);

        return ResponseEntity.ok(ApiResponse.OK);
    }
}
