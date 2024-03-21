package com.woodada.core.diary.adapter.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.support.ApiResponse;
import com.woodada.core.diary.adapter.in.request.WriteDiaryRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diary")
public class WriteDiaryAdapter {

    @PostMapping
    ResponseEntity<ApiResponse<Void>> writeDiary(WddMember wddMember, @RequestBody @Valid WriteDiaryRequest request) {

        return ResponseEntity.ok(ApiResponse.OK);
    }
}
