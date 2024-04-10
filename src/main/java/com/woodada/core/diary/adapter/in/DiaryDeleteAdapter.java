package com.woodada.core.diary.adapter.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.diary.application.port.in.DiaryDeleteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryDeleteAdapter {

    private final DiaryDeleteUseCase diaryDeleteUseCase;

    public DiaryDeleteAdapter(final DiaryDeleteUseCase diaryDeleteUseCase) {
        this.diaryDeleteUseCase = diaryDeleteUseCase;
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> deleteDiary(WddMember wddMember,  @PathVariable Long id) {
        diaryDeleteUseCase.deleteDiary(wddMember, id);

        return ResponseEntity.ok(ApiResponse.OK);
    }
}
