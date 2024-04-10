package com.woodada.core.diary.adapter.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.diary.adapter.in.request.DiaryModifyRequest;
import com.woodada.core.diary.application.port.in.DiaryModifyCommand;
import com.woodada.core.diary.application.port.in.DiaryModifyUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryModifyAdapter {

    private final DiaryModifyUseCase diaryModifyUseCase;

    public DiaryModifyAdapter(final DiaryModifyUseCase diaryModifyUseCase) {
        this.diaryModifyUseCase = diaryModifyUseCase;
    }

    @PutMapping
    ResponseEntity<ApiResponse<Void>> modifyDiary(WddMember wddMember, @RequestBody @Valid DiaryModifyRequest request) {
        final DiaryModifyCommand command = new DiaryModifyCommand(request.getId(), request.getTitle(), request.getContents());
        diaryModifyUseCase.modify(wddMember, command);

        return ResponseEntity.ok(ApiResponse.OK);
    }
}
