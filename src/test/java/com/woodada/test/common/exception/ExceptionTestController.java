package com.woodada.test.common.exception;

import com.woodada.common.exception.WddException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
@RequestMapping("/exception")
class ExceptionTestController {

    @PostMapping("/illegal-argument-exception")
    void illegalArgumentException() {
        throw new IllegalArgumentException("illegal argument");
    }

    @GetMapping("/http-request-method-not-supported-exception")
    void httpRequestMethodNotSupportedException(@RequestBody @Valid TestRequestBody requestBody) {
    }

    @PostMapping("/http-message-not-readable-exception")
    void httpMessageNotReadableException(@RequestBody @Valid TestRequestBody requestBody) {
    }

    @PostMapping("/method-argument-not-valid-exception")
    void methodArgumentNotValidException(@RequestBody @Valid TestRequestBody requestBody) {
    }

    @GetMapping("/business-exception")
    void wddException() {
        throw new WddException(TestException.TEST);
    }

    @GetMapping("/internal-server-error")
    void internalServerError() throws Exception {
        throw new Exception("서버 에러");
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestRequestBody {
        @NotEmpty
        @Size(max = 3)
        private String string;
        @NotNull
        @Positive
        private Integer integer;
        @NotEmpty
        private List<String> list;
    }
}

